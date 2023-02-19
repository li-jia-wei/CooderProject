package com.cooder.cooder.project.app.main.biz.account

import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.project.app.main.route.RouteFlag
import com.cooder.cooder.project.app.main.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:13
 *
 * 介绍：ProfileDetailActivity
 */
@Route(path = RoutePath.ACTIVITY_PROFILE_DETAIL, extras = RouteFlag.FLAG_LOGIN or RouteFlag.FLAG_VIP)
class ProfileDetailActivity : CoBaseActivity() {

}