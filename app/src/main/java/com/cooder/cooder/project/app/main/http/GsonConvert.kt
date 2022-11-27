package com.cooder.cooder.project.app.main.http

import com.cooder.cooder.library.restful.CooderConvert
import com.cooder.cooder.library.restful.CooderResponse
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
class GsonConvert : CooderConvert {
	
	private val gson = Gson()
	
	/**
	 * 转换
	 */
	override fun <T> convert(rawData: String, dataType: Type): CooderResponse<T> {
		val response = CooderResponse<T>()
		response.rawData = rawData
		try {
			val jsonObject = JSONObject(rawData)
			response.code = jsonObject.optInt("code")
			response.msg = jsonObject.optString("msg")
			val data = jsonObject.opt("data")
			if (data is JSONObject || data is JSONArray) {
				if (response.code == CooderResponse.SUCCESS) {
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
			response.code = CooderResponse.EXCEPTION
			response.msg = e.message
		}
		return response
	}
}