package com.cooder.cooder.project.app.main.biz

import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.project.app.main.route.RouteFlag
import com.cooder.cooder.project.app.main.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：AuthenticationActivity
 */
@Route(path = RoutePath.ACTIVITY_PROFILE_AUTHENTICATION, extras = RouteFlag.FLAG_AUTHENTICATION)
class AuthenticationActivity : CooderBaseActivity() {
	
}