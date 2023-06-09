package com.cooder.project.biz_login.api

import com.cooder.library.library.restful.CoCall
import com.cooder.library.library.restful.annotation.Filed
import com.cooder.library.library.restful.annotation.POST

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/23 04:03
 *
 * 介绍：账号相关接口
 */
interface AccountApi : com.cooder.project.common.http.Api {
	
	/**
	 * 登录接口
	 */
	@POST("/user/login")
	fun login(
		@Filed("userName") username: String,
		@Filed("password") password: String
	): CoCall<String>
	
	/**
	 * 注册接口
	 */
	@POST("/user/registration")
	fun register(
		@Filed("userName") username: String,    // 用户名
		@Filed("password") password: String,    // 密码
		@Filed("imoocId") imoocId: String,      // 慕课ID
		@Filed("orderId") orderId: String       // 课程编号
	): CoCall<String>
}