package com.cooder.cooder.project.app.main.http.interceptor

import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.restful.CooderInterceptor
import com.cooder.cooder.project.common.util.PreferencesUtil

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:03
 *
 * 介绍：Biz拦截器
 */
class BizInterceptor : CooderInterceptor {
	
	override fun requestIntercept(chain: CooderInterceptor.RequestChain): Boolean {
		val request = chain.request()
		request.addHeader("Connection", "Keep-alive")
		request.addHeader("Accept-Charset", "utf-8")
		request.addHeader("auth-token", "MTU5Mjg1MDg3NDcwNw11.26==")
		val boardingPass = PreferencesUtil.getString("boarding-pass", "")
		request.addHeader("boarding-pass", boardingPass!!)
		CooderLog.i("request: ${request.getCompleteUrl()}")
		return false
	}
	
	override fun responseIntercept(chain: CooderInterceptor.ResponseChain): Boolean {
		return false
	}
}