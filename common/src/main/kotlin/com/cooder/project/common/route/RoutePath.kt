package com.cooder.project.common.route

import androidx.annotation.StringDef

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/24 00:36
 *
 * 介绍：route flag
 */
object RoutePath {
	
	/**
	 * app 模块
	 */
	object App {
		
		const val SERVICE_ROUTE_DEGRADE_GLOBAL = "/app/route/degrade_global/service"
		
		const val ACTIVITY_ROUTE_DEGRADE_GLOBAL = "/app/route/degrade_global/activity"
	}
	
	/**
	 * biz-login 模块
	 */
	object BizLogin {
		
		const val ACTIVITY_LOGIN = "/biz_login/login/activity"
		
		const val ACTIVITY_REGISTER = "/biz_login/register/activity"
		
		const val SERVICE_LOGIN = "/biz_login/login/service"
	}
	
	/**
	 * biz-home 模块
	 */
	object BizHome {
		
		const val ACTIVITY_GOODS_GOODS_LIST = "/biz_home/goods/goods_list/activity"
	}
	
	/**
	 * biz-detail 模块
	 */
	object BizDetail {
		
		const val ACTIVITY_DETAIL = "/biz_detail/detail/activity"
	}
	
	object BizSearch {
		const val ACTIVITY_SEARCH = "/biz_search/search/activity"
	}
	
	@StringDef(
		App.SERVICE_ROUTE_DEGRADE_GLOBAL,
		BizLogin.SERVICE_LOGIN
	)
	annotation class ServiceDef
	
	@StringDef(
		App.ACTIVITY_ROUTE_DEGRADE_GLOBAL,
		BizLogin.ACTIVITY_LOGIN,
		BizLogin.ACTIVITY_REGISTER,
		BizHome.ACTIVITY_GOODS_GOODS_LIST,
		BizDetail.ACTIVITY_DETAIL,
		BizSearch.ACTIVITY_SEARCH
	)
	annotation class ActivityRef
}