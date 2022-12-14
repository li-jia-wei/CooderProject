package com.cooder.cooder.project.app.main.http.api

import com.cooder.cooder.library.restful.CooderCall
import com.cooder.cooder.project.app.main.model.HomeModel
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/9 22:50
 *
 * 介绍：首页模块接口
 */
interface HomeApi : Api {
	
	/**
	 * 商品列表
	 */
	@GET("home/{categoryId}")
	fun queryTabCategoryList(@Path("categoryId") categoryId: String): CooderCall<HomeModel>
}