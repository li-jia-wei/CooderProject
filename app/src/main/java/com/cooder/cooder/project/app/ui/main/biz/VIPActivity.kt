package com.cooder.cooder.project.app.ui.main.biz

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.project.app.ui.main.route.RouteFlag
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：VIPActivity
 */
@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
class VIPActivity : CooderBaseActivity() {
	 
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
}