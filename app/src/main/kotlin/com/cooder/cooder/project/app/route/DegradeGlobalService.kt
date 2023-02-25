package com.cooder.cooder.project.app.route

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:56
 *
 * 介绍：全局降级服务，当路由的时候，目标页面不存在，此时定位到同意路由错误页面上
 */
@Route(path = RoutePath.SERVICE_ROUTE_DEGRADE_GLOBAL)
class DegradeGlobalService : DegradeService {
	
	override fun init(context: Context?) {
	
	}
	
	override fun onLost(context: Context?, postcard: Postcard?) {
		CoRoute.startActivity(RoutePath.ACTIVITY_ROUTE_DEGRADE_GLOBAL, greenChannel = true)
	}
}