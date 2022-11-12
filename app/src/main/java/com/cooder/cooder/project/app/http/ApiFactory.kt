package com.cooder.cooder.project.app.http

import com.cooder.cooder.library.restful.CooderRestful

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:01
 *
 * 介绍：ApiFactory
 */
object ApiFactory {
	
	private const val baseUrl = "http://10.0.2.2:8080/CooderServer/"
	
	private val cooderRestful = CooderRestful(baseUrl, RetrofitCallFactory(baseUrl))
	
	init {
		cooderRestful.addInterceptor(BizInterceptor())
	}
	
	fun <T> create(service: Class<T>): T {
		return cooderRestful.create(service)
	}
}