package com.cooder.project.biz_detail.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.DetailModel

interface GoodsApi : Api {
	
	@GET("goods/detail/{id}")
	fun queryDetail(@Path("id") goodsId: String): CoCall<DetailModel>
}