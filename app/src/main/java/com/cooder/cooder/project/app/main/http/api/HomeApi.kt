package com.cooder.cooder.project.app.main.http.api

import com.cooder.cooder.library.restful.CooderCall
import com.cooder.cooder.library.restful.annotation.Filed
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.main.model.HomeModel

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
	fun queryTabCategoryList(
		@Path("categoryId") categoryId: String,
		@Filed("pageIndex") pageIndex: Int,
		@Filed("pageSize") pageSize: Int
	): CooderCall<HomeModel>
}