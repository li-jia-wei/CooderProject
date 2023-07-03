package com.cooder.project.biz_home.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.biz_home.model.SubcategoryMo
import com.cooder.project.biz_home.model.TabCategoryMo
import com.cooder.project.common.http.Api

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
	
	@GET("/category/categories")
	@CacheStrategy(CacheStrategy.Type.NET_ONLY)
	fun queryCategoryList(): CoCall<List<TabCategoryMo>>
	
	@GET("/category/subcategories/{categoryId}")
	fun querySubcategoryList(
		@Path("categoryId") categoryId: String
	): CoCall<List<SubcategoryMo>>
}