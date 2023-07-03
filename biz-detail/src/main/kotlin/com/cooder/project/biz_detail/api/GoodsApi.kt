package com.cooder.project.biz_detail.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.biz_detail.model.DetailMo
import com.cooder.project.common.http.Api

interface GoodsApi : Api {
	
	@GET("/goods/detail/{id}")
	fun queryDetail(@Path("id") goodsId: String): CoCall<DetailMo>
}