package com.cooder.cooder.project.app.main.http.interceptor

import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.restful.CooderInterceptor
import com.cooder.cooder.library.restful.CooderResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.route.DegradeGlobalActivity
import com.cooder.cooder.project.app.main.route.RoutePath
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
class HttpCodeInterceptor : CooderInterceptor {
	
	override fun requestIntercept(chain: CooderInterceptor.RequestChain): Boolean {
		return false
	}
	
	override fun responseIntercept(chain: CooderInterceptor.ResponseChain): Boolean {
		val response = chain.response()
		when (response.code) {
			CooderResponse.RC_NEED_LOGIN -> {
				ARouter.getInstance()
					.build(RoutePath.ACTIVITY_ACCOUNT_LOGIN)
					.navigation()
			}
			CooderResponse.RC_AUTH_TOKEN_EXPIRED, CooderResponse.RC_AUTH_TOKEN_INVALID -> {
				var helpUrl: String? = null
				if (response.errorData != null) {
					helpUrl = response.errorData!!["helpUrl"]
				}
				ARouter.getInstance()
					.build(RoutePath.ACTIVITY_DEGRADE_GLOBAL)
					.withString(DegradeGlobalActivity.DEGRADE_TITLE, AppGlobals.getBaseContext().getString(R.string.token_degrade_title))
					.withString(DegradeGlobalActivity.DEGRADE_DESC, response.msg)
					.withString(DegradeGlobalActivity.DEGRADE_ACTION, helpUrl)
					.navigation()
			}
		}
		return false
	}
}