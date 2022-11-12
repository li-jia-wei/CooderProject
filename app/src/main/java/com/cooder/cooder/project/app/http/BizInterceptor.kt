package com.cooder.cooder.project.app.http

import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.restful.CooderInterceptor

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
	
	override fun intercept(chain: CooderInterceptor.Chain): Boolean {
		if (chain.isRequestPeriod) {
			val request = chain.request()
			request.addHeader("Connection", "Keep-alive")       // 设置长连接
			request.addHeader("Accept-Charset", "utf-8")        // 设置请求的字符编码集
			request.addHeader("Accept-Language", "en,zh")       // 设置请求的语言
		} else {
			CooderLog.i(chain.request().getCompleteUrl())
			CooderLog.i(chain.response()!!.rawData)
		}
		return false
	}
}