package com.cooder.project.biz_detail.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.POST
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.biz_detail.model.FavoriteMo
import com.cooder.project.common.http.Api

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/20 21:48
 *
 * 介绍：FavoriteApi
 */
interface FavoriteApi : Api {
	
	@POST("/favorites/{goodsId}")
	fun favorite(@Path("goodsId") goodsId: String): CoCall<FavoriteMo>
	
}