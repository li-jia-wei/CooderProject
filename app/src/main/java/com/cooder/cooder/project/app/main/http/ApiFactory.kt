package com.cooder.cooder.project.app.main.http

import com.cooder.cooder.library.restful.CooderRestful
import com.cooder.cooder.project.app.main.http.interceptor.BizInterceptor
import com.cooder.cooder.project.app.main.http.interceptor.HttpStatusInterceptor

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:01
 *
 * 介绍：ApiFactory
 *
 * [auto-token更新网站](https://class.imooc.com/course/qadetail/235042)
 * [API网站](http://api.devio.org/as/swagger-ui.html)
 */
object ApiFactory {
	
	private const val baseUrl = "http://api.devio.org/as/"
	
	private val cooderRestful = CooderRestful(baseUrl, RetrofitCallFactory(baseUrl))
	
	init {
		cooderRestful.addInterceptor(BizInterceptor())
		cooderRestful.addInterceptor(HttpStatusInterceptor())
	}
	
	@JvmStatic
	fun <T> create(service: Class<T>): T where T : Api {
		return cooderRestful.create(service)
	}
	
	/**
	 * 使用网络请求需要实现这个标记接口
	 */
	interface Api
}