package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CooderCall
import com.cooder.cooder.library.restful.annotation.Filed
import com.cooder.cooder.library.restful.annotation.GET
import org.json.JSONObject

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:21
 *
 * 介绍：测试API
 */
interface TestApi {
	
	@GET("/CooderServer/test")
	fun listCities(@Filed("name") name: String): CooderCall<JSONObject>
}