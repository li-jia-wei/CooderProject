package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.library.restful.annotation.Filed
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.model.DetailModel
import com.cooder.cooder.project.app.model.GoodsList

interface GoodsApi : Api {
	
	@GET("goods/detail/{id}")
	fun queryDetail(@Path("id") goodsId: String): CoCall<DetailModel>
	
	/**
	 * 查询商品页
	 */
	@GET("goods/goods/{categoryId}")
	fun queryGoodsList(
		@CacheStrategy cacheStrategyType: CacheStrategy.Type,
		@Path("categoryId") categoryId: String,
		@Filed("subcategoryId") subcategoryId: String,
		@Filed("pageSize") pageSize: Int,
		@Filed("pageIndex") pageIndex: Int
	): CoCall<GoodsList>
}