package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.model.Subcategory
import com.cooder.cooder.project.app.model.TabCategory

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
	@CacheStrategy(CacheStrategy.Type.CACHE_NET_CACHE)
	fun querySubcategoryList(@Path("categoryId") categoryId: String): CoCall<List<Subcategory>>
}