package com.cooder.cooder.project.app.http.api

import com.cooder.cooder.library.restful.CooderCall
import com.cooder.cooder.library.restful.annotation.*
import com.cooder.cooder.project.app.http.ApiFactory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 10:19
 *
 * 介绍：Account接口
 */
interface AccountApi : ApiFactory.Api {
	
	/**
	 * 登录
	 */
	@POST("user/login")
	fun login(
		@Filed("userName") username: String,    // 用户名
		@Filed("password") password: String     // 密码
	): CooderCall<String>
	
	/**
	 * 注册
	 */
	@POST("user/registration")
	fun register(
		@Filed("userName") username: String,    // 用户名
		@Filed("password") password: String,    // 密码
		@Filed("imoocId") imoocId: String,      // 慕课ID
		@Filed("orderId") orderId: String       // 课程编号
	): CooderCall<String>
}