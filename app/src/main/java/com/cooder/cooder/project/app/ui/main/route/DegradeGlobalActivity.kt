package com.cooder.cooder.project.app.ui.main.route

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 20:01
 *
 * 介绍：DegradeGlobalActivity
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : CooderBaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_degrade_global)
	}
}