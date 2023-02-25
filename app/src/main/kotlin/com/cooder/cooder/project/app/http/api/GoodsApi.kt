package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.Filed
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.model.GoodsList

interface GoodsApi : Api {
	
	@GET("goods/goods/{categoryId}")
	fun queryGoodsList(
		@Path("categoryId") categoryId: String,
		@Filed("subcategoryId") subcategoryId: String,
		@Filed("pageSize") pageSize: Int,
		@Filed("pageIndex") pageIndex: Int
	): CoCall<GoodsList>
}