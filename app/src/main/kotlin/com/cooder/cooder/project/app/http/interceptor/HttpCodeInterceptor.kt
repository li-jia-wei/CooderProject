package com.cooder.cooder.project.app.http.interceptor

import android.os.Bundle
import com.cooder.cooder.library.restful.CoInterceptor
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.util.AppGlobals

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/23 18:54
 *
 * 介绍：HttpCode拦截器，根据response的code自动路由到相关页面
 */
class HttpCodeInterceptor : CoInterceptor {
	
	override fun requestIntercept(chain: CoInterceptor.RequestChain): Boolean {
		return false
	}
	
	override fun responseIntercept(chain: CoInterceptor.ResponseChain): Boolean {
		val response = chain.response()
		when (response.code) {
			CoResponse.RC_NEED_LOGIN -> {
				CoRoute.startActivity(RoutePath.ACTIVITY_BIZ_ACCOUNT_LOGIN)
				return true
			}
			
			CoResponse.RC_AUTH_TOKEN_EXPIRED, CoResponse.RC_AUTH_TOKEN_INVALID -> {
				var helpUrl: String? = null
				if (response.errorData != null) {
					helpUrl = response.errorData!!["helpUrl"]
				}
				val bundle = Bundle()
				bundle.putString("degradeTitle", AppGlobals.getBaseContext().getString(R.string.token_degrade_title))
				bundle.putString("degradeDesc", response.message)
				bundle.putString("degradeAction", helpUrl)
				CoRoute.startActivity(RoutePath.ACTIVITY_ROUTE_DEGRADE_GLOBAL, bundle)
				return true
			}
		}
		return false
	}
}