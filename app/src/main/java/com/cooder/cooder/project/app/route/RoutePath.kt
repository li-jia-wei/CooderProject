package com.cooder.cooder.project.app.route

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
	
	const val SERVICE_ROUTE_DEGRADE_GLOBAL = "/route/degrade/global/service"
	
	const val ACTIVITY_ROUTE_DEGRADE_GLOBAL = "/route/degrade/global/activity"
	
	const val ACTIVITY_BIZ_ACCOUNT_LOGIN = "/biz/account/login/activity"
	const val ACTIVITY_BIZ_ACCOUNT_REGISTER = "/biz/account/register/activity"
	
	const val ACTIVITY_BIZ_GOODS_GOODS_LIST = "/biz/goods/goods/list/activity"
	
	@StringDef(SERVICE_ROUTE_DEGRADE_GLOBAL)
	annotation class ServiceDef
	
	@StringDef(
		ACTIVITY_ROUTE_DEGRADE_GLOBAL,
		ACTIVITY_BIZ_ACCOUNT_LOGIN,
		ACTIVITY_BIZ_ACCOUNT_REGISTER,
		ACTIVITY_BIZ_GOODS_GOODS_LIST
	)
	annotation class ActivityRef
}