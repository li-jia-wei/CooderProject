package com.cooder.cooder.project.app.ui.main.route

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:56
 *
 * 介绍：全局降级服务，当路由的时候，目标页面不存在，此时定位到同意路由错误页面上
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {
	
	override fun init(context: Context?) {
	
	}
	
	override fun onLost(context: Context?, postcard: Postcard?) {
		ARouter.getInstance()
			.build("/degrade/global/activity")
			.greenChannel()
			.navigation()
	}
}