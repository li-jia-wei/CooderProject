package com.cooder.cooder.project.app.http.interceptor

import com.cooder.cooder.library.restful.CoInterceptor
import com.cooder.cooder.project.app.biz.account.AccountManager

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:03
 *
 * 介绍：Biz拦截器
 */
class BizInterceptor : CoInterceptor {
	
	override fun requestIntercept(chain: CoInterceptor.RequestChain): Boolean {
		val request = chain.request()
		request.addHeader("Connection", "Keep-alive")
		request.addHeader("Accept-Charset", "utf-8")
		request.addHeader("auth-token", "MTU5Mjg1MDg3NDcwNw11.26==")
		request.addHeader("boarding-pass", AccountManager.getBoardingPass())
		return false
	}
	
	override fun responseIntercept(chain: CoInterceptor.ResponseChain): Boolean {
		return false
	}
}