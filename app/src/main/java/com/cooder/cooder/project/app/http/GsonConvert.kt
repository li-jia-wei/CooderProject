package com.cooder.cooder.project.app.http

import com.cooder.cooder.library.restful.CooderConvert
import com.cooder.cooder.library.restful.CooderResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 17:40
 *
 * 介绍：GsonConvert
 */
class GsonConvert : CooderConvert {
	
	private val gson = Gson()
	
	override fun <T> convert(rawData: String, dataType: Type): CooderResponse<T> {
		val response = CooderResponse<T>()
		response.rawData = rawData
		try {
			val jsonObject = JSONObject(rawData)
			response.code = jsonObject.optInt("code")
			response.msg = jsonObject.optString("msg")
			val data = jsonObject.optString("data")
			if (response.code == CooderResponse.SUCCESS) {
				response.data = gson.fromJson(data, dataType)
			} else {
				response.errorData = gson.fromJson(data, object : TypeToken<MutableMap<String, String>>() {}.type)
			}
		} catch (e: Exception) {
			e.printStackTrace()
			response.code = CooderResponse.EXCEPTION
			response.msg = e.message
		}
		return response
	}
}