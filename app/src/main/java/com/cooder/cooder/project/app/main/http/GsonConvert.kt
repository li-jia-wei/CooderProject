package com.cooder.cooder.project.app.main.http

import com.cooder.cooder.library.restful.CoConvert
import com.cooder.cooder.library.restful.CoResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 17:40
 *
 * 介绍：Gson转换
 */
class GsonConvert : CoConvert {
	
	private val gson = Gson()
	
	/**
	 * 转换
	 */
	override fun <T> convert(rawData: String, dataType: Type): CoResponse<T> {
		val response = CoResponse<T>()
		response.rawData = rawData
		try {
			val jsonObject = JSONObject(rawData)
			response.code = jsonObject.optInt("code")
			response.message = jsonObject.optString("msg")
			val data = jsonObject.opt("data")
			if (data is JSONObject || data is JSONArray) {
				if (response.code == CoResponse.SUCCESS) {
					response.data = gson.fromJson(data.toString(), dataType)
				} else {
					response.errorData = gson.fromJson(data.toString(), object : TypeToken<MutableMap<String, String>>() {}.type)
				}
			} else {
				@Suppress("UNCHECKED_CAST")
				response.data = data as T?
			}
		} catch (e: Exception) {
			e.printStackTrace()
			response.code = CoResponse.EXCEPTION
			response.message = e.message
		}
		return response
	}
}