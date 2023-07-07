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
	
	const val TYPE_DEGRADE_HTTP = "config"
	const val KEY_DEGRADE_HTTP = "degrade_http"
	
	private const val HTTPS = "https://api.devio.org/as/"
	private const val HTTP = "http://api.devio.org/as/"
	
	val degradeToHttp = CoStorage.getCache(TYPE_DEGRADE_HTTP, KEY_DEGRADE_HTTP, false)!!
	
	private val baseUrl = if (degradeToHttp) HTTP else HTTPS
	
	private val restful = CoRestful(baseUrl, RetrofitCallFactory(baseUrl))
	
	init {
		restful.addInterceptor(BizInterceptor())
		restful.addInterceptor(HttpCodeInterceptor())
		CoStorage.saveCache(TYPE_DEGRADE_HTTP, KEY_DEGRADE_HTTP, false)
	}
	
	/**
	 * @param service 返回的数据类型
	 * @param cancelInterceptors 不参与拦截的拦截器
	 */
	@JvmStatic
	@JvmOverloads
	fun <T : Api> create(
		service: Class<T>,
		ignoreInterceptors: List<Class<out CoInterceptor>>? = null,
		extraInterceptors: List<Class<out CoInterceptor>>? = null
	): T {
		return restful.create(service, ignoreInterceptors, extraInterceptors)
	}
}