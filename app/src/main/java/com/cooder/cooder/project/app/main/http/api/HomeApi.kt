package com.cooder.cooder.project.app.main.http.api

import com.cooder.cooder.library.restful.CooderCall
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.project.app.main.model.TabCategory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/6 21:39
 *
 * 介绍：HomeApi
 */
interface HomeApi : Api {
	
	@GET("category/categories")
	fun queryTab(): CooderCall<List<TabCategory>>
}