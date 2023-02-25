package com.cooder.cooder.project.app.http

import com.cooder.cooder.library.restful.CoInterceptor
import com.cooder.cooder.library.restful.CoRestful
import com.cooder.cooder.project.app.http.api.Api
import com.cooder.cooder.project.app.http.interceptor.BizInterceptor
import com.cooder.cooder.project.app.http.interceptor.HttpCodeInterceptor

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
 *
 * [API网站](http://api.devio.org/as/swagger-ui.html)
 */
object ApiFactory {
	
	private const val baseUrl = "http://api.devio.org/as/"
	
	private val cooderRestful = CoRestful(baseUrl, RetrofitCallFactory(baseUrl))
	
	init {
		cooderRestful.addInterceptor(BizInterceptor())
		cooderRestful.addInterceptor(HttpCodeInterceptor())
	}
	
	/**
	 * @param service 返回的数据类型
	 * @param cancelInterceptors 不参与拦截的拦截器
	 */
	@JvmStatic
	@JvmOverloads
	fun <T : Api> create(service: Class<T>, ignoreInterceptors: List<Class<out CoInterceptor>>? = null, extraInterceptors: List<Class<out CoInterceptor>>? = null): T {
		return cooderRestful.create(service, ignoreInterceptors, extraInterceptors)
	}
}