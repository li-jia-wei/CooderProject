package com.cooder.project.common.http

import android.os.Bundle
import com.cooder.library.library.restful.CoInterceptor
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.util.AppGlobals
import com.cooder.project.common.R
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.route.RoutePath

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
				CoRoute.startActivity(RoutePath.BizLogin.ACTIVITY_LOGIN)
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
				CoRoute.startActivity(RoutePath.App.ACTIVITY_ROUTE_DEGRADE_GLOBAL, bundle)
			}
		}
		return false
	}
}