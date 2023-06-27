package com.cooder.project.biz_home.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.restful.annotation.Filed
import com.cooder.library.library.restful.annotation.GET
import com.cooder.library.library.restful.annotation.Path
import com.cooder.project.common.http.Api
import com.cooder.project.pub_mod.model.HomeModel

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/9 22:50
 *
 * 介绍：首页模块接口
 */
interface HomeApi : Api {
	
	/**
	 * 查询顶部标题对应的数据
	 */
	@GET("home/{categoryId}")
	fun queryTabCategoryList(
		@Path("categoryId") categoryId: String,
		@Filed("pageIndex") pageIndex: Int,
		@Filed("pageSize") pageSize: Int,
		@CacheStrategy type: CacheStrategy.Type
	): CoCall<HomeModel>
}