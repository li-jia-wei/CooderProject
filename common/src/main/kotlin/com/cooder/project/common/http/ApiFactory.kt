package com.cooder.project.common.http

import com.cooder.library.library.cache.CoStorage
import com.cooder.library.library.restful.CoInterceptor
import com.cooder.library.library.restful.CoRestful

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
	
	const val KEY_DEGRADE_HTTP = "configure::degrade_http"
	
	private const val HTTPS = "https://api.devio.org/as/"
	private const val HTTP = "http://api.devio.org/as/"
	
	val degradeToHttp = CoStorage.getCache(com.cooder.project.common.http.ApiFactory.KEY_DEGRADE_HTTP, false)!!
	
	private val baseUrl =
		if (com.cooder.project.common.http.ApiFactory.degradeToHttp) com.cooder.project.common.http.ApiFactory.HTTP else com.cooder.project.common.http.ApiFactory.HTTPS
	
	private val cooderRestful = CoRestful(
		com.cooder.project.common.http.ApiFactory.baseUrl,
		com.cooder.project.common.http.RetrofitCallFactory(com.cooder.project.common.http.ApiFactory.baseUrl)
	)
	
	init {
		com.cooder.project.common.http.ApiFactory.cooderRestful.addInterceptor(com.cooder.project.common.http.BizInterceptor())
		com.cooder.project.common.http.ApiFactory.cooderRestful.addInterceptor(com.cooder.project.common.http.HttpCodeInterceptor())
		CoStorage.saveCache(com.cooder.project.common.http.ApiFactory.KEY_DEGRADE_HTTP, false)
	}
	
	/**
	 * @param service 返回的数据类型
	 * @param cancelInterceptors 不参与拦截的拦截器
	 */
	@JvmStatic
	@JvmOverloads
	fun <T : com.cooder.project.common.http.Api> create(
		service: Class<T>,
		ignoreInterceptors: List<Class<out CoInterceptor>>? = null,
		extraInterceptors: List<Class<out CoInterceptor>>? = null
	): T {
		return com.cooder.project.common.http.ApiFactory.cooderRestful.create(service, ignoreInterceptors, extraInterceptors)
	}
}