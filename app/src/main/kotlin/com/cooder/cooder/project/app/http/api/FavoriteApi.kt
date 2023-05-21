package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.POST
import com.cooder.cooder.library.restful.annotation.Path
import com.cooder.cooder.project.app.model.Favorite

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
	
	@POST("favorites/{goodsId}")
	fun favorite(@Path("goodsId") goodsId: String): CoCall<Favorite>
}