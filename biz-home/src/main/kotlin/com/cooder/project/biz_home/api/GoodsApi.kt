package com.cooder.project.biz_home.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.restful.annotation.Filed
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.GoodsListMo

interface GoodsApi : Api {
	
	/**
	 * 查询商品页
	 */
	@GET("/goods/goods/{categoryId}")
	fun queryGoodsList(
		@CacheStrategy cacheStrategyType: CacheStrategy.Type,
		@Path("categoryId") categoryId: String,
		@Filed("subcategoryId") subcategoryId: String,
		@Filed("pageSize") pageSize: Int,
		@Filed("pageIndex") pageIndex: Int
	): CoCall<GoodsListMo>
}