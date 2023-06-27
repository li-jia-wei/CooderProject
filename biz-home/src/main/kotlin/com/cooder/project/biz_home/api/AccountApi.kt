package com.cooder.project.biz_home.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.restful.annotation.GET
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.UserProfile

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 10:19
 *
 * 介绍：账号模块接口
 */
interface AccountApi : Api {
	
	/**
	 * 个人中心
	 */
	@GET("user/profile")
	@CacheStrategy(CacheStrategy.Type.CACHE_NET_CACHE)
	fun profile(): CoCall<UserProfile>
}