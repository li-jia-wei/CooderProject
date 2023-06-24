package com.cooder.cooder.biz_home.api

import com.cooder.cooder.common.http.Api
import com.cooder.cooder.library.restful.CoCall
import com.cooder.cooder.library.restful.annotation.GET
import com.cooder.cooder.pub_mod.model.CourseNotice

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/9 22:04
 *
 * 介绍：通知模块接口
 */
interface NoticeApi : Api {
	
	/**
	 * 获取通知列表
	 */
	@GET("notice")
	fun notice(): CoCall<CourseNotice>
}