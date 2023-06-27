package com.cooder.project.biz_home.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.Subcategory
import com.cooder.project.pub_mod.model.TabCategory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/6 21:39
 *
 * 介绍：类别模块接口
 */
interface CategoryApi : Api {
	
	@GET("category/categories")
	@CacheStrategy(CacheStrategy.Type.NET_ONLY)
	fun queryCategoryList(): CoCall<List<TabCategory>>
	
	@GET("category/subcategories/{categoryId}")
	fun querySubcategoryList(
		@Path("categoryId") categoryId: String
	): CoCall<List<Subcategory>>
}