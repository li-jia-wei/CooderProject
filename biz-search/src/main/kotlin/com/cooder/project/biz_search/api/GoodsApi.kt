package com.cooder.project.biz_search.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.Filed
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.POST
import com.cooder.project.biz_search.model.QuickSearchListMo
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.GoodsListMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/2 13:48
 *
 * 介绍：商品API
 */
interface GoodsApi : Api {
	
	/**
	 * 商品快搜
	 */
	@GET("/goods/search/quick")
	fun querySearchQuick(
		@Filed("keyWord") keyWord: String
	): CoCall<QuickSearchListMo>
	
	/**
	 * 商品搜索
	 */
	@POST("/goods/search", formPost = false)
	fun querySearchGoods(
		@Filed("keyWord") keyWord: String,
		@Filed("pageIndex") pageIndex: Int,
		@Filed("pageSize") pageSize: Int
	): CoCall<GoodsListMo>
}