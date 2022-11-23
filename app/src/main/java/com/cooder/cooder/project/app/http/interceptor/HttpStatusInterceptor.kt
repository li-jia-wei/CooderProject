package com.cooder.cooder.project.app.http.interceptor

import com.cooder.cooder.library.restful.CooderInterceptor

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/23 18:54
 *
 * 介绍：Http状态拦截器，根据response的code自动路由到相关页面
 */
class HttpStatusInterceptor : CooderInterceptor {
	
	override fun requestIntercept(chain: CooderInterceptor.RequestChain): Boolean {
		
		return false
	}
	
	override fun responseIntercept(chain: CooderInterceptor.ResponseChain): Boolean {
		
		return false
	}
}