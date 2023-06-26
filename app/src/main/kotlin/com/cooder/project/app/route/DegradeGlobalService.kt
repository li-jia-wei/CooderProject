package com.cooder.project.app.route

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.cooder.cooder.common.route.CoRoute
import com.cooder.cooder.common.route.RoutePath
import com.cooder.cooder.library.log.CoLog

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:56
 *
 * 介绍：全局降级服务，当路由的时候，目标页面不存在，此时定位到同意路由错误页面上
 */
@Route(path = RoutePath.App.SERVICE_ROUTE_DEGRADE_GLOBAL)
class DegradeGlobalService : DegradeService {

    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        CoLog.i(postcard)
        val bundle = Bundle()
        bundle.putString("degradeTitle", "页面不存在")
        bundle.putString("degradeDesc", "当前页面被程序员吃了，正在催吐中～")
        bundle.putString("degradeAction", null)
        CoRoute.startActivity(RoutePath.App.ACTIVITY_ROUTE_DEGRADE_GLOBAL, bundle, greenChannel = true)
    }
}