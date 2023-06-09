package com.cooder.project.common.http

import com.cooder.library.library.restful.*
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 02:20
 *
 * 介绍：RetrofitCallFactory
 */
class RetrofitCallFactory(baseUrl: String) : CoCall.Factory {
	
	private val apiService: ApiService
	private val convert: CoConvert = GsonConvert()
	
	init {
		val retrofit = Retrofit.Builder()
			.baseUrl(baseUrl)
			.build()
		apiService = retrofit.create(ApiService::class.java)
	}
	
	override fun newCall(request: CoRequest): CoCall<Any> {
		return RetrofitCall(request)
	}
	
	internal inner class RetrofitCall<T>(
		private val request: CoRequest
	) : CoCall<T> {
		
		override fun enqueue(callback: CoCallback<T>) {
			val realCall = createRealCall(request)
			realCall.enqueue(object : Callback<ResponseBody> {
				override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
					callback.onSuccess(parseResponse(response))
				}
				
				override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
					callback.onFailed(t)
				}
			})
		}
		
		private fun parseResponse(response: Response<ResponseBody>): CoResponse<T> {
			var rawData: String? = null
			if (response.isSuccessful) {
				val body = response.body()
				if (body != null) rawData = body.string()
			} else {
				val body = response.errorBody()
				if (body != null) rawData = body.string()
			}
			return convert.convert(rawData!!, request.returnType!!)
		}
		
		/**
		 * 创建真实调用
		 */
		private fun createRealCall(request: CoRequest): Call<ResponseBody> {
			return when (request.httpMethod) {
				CoRequest.METHOD.GET -> {
					apiService.get(request.headers, request.getCompleteUrl(), request.parameters)
				}
				
				CoRequest.METHOD.POST -> {
					val parameters = request.parameters!!
					val requestBody: RequestBody = if (request.formPost) {
						val builder = FormBody.Builder()
						for ((key, value) in parameters) {
							builder.add(key, value)
						}
						builder.build()
					} else {
						val jsonObjet = JSONObject()
						for ((key, value) in parameters) {
							jsonObjet.put(key, value)
						}
						RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObjet.toString())
					}
					apiService.post(request.headers, request.getCompleteUrl(), requestBody)
				}
				
				else -> {
					throw IllegalStateException("Currently, CoRestful support only GET and POST request method.")
				}
			}
		}
	}
	
	interface ApiService {
		
		@GET
		fun get(
			@HeaderMap headers: MutableMap<String, String>?,
			@Url url: String,
			@QueryMap(encoded = true) params: MutableMap<String, String>?
		): Call<ResponseBody>
		
		@POST
		fun post(
			@HeaderMap headers: MutableMap<String, String>?,
			@Url url: String,
			@Body body: RequestBody
		): Call<ResponseBody>
	}
}