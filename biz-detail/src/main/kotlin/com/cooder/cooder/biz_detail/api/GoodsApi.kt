package com.cooder.cooder.biz_detail.api

import com.cooder.cooder.common.http.Api
import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.pub_mod.model.DetailModel

interface GoodsApi : Api {

    @GET("goods/detail/{id}")
    fun queryDetail(@Path("id") goodsId: String): CoCall<DetailModel>
}