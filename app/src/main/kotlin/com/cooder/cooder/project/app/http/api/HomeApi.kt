package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.library.restful.annotation.Filed
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.model.HomeModel
import com.cooder.cooder.project.app.model.TabCategory

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
	 * 查询首页顶部Tab
	 */
	@GET("category/categories")
	@CacheStrategy(CacheStrategy.Type.NET_ONLY)
	fun queryCategoryList(): CoCall<List<TabCategory>>
	
	/**
	 * 查询顶部标题对应的数据
	 */
	@GET("home/{categoryId}")
	fun queryTabCategoryList(
		@CacheStrategy type: CacheStrategy.Type,
		@Path("categoryId") categoryId: String,
		@Filed("pageIndex") pageIndex: Int,
		@Filed("pageSize") pageSize: Int
	): CoCall<HomeModel>
}