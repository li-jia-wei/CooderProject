package com.cooder.cooder.project.app.ui.main.biz

import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/9 11:41
 *
 * 介绍：DegradeServiceActivity
 */
class RouteActivity : CooderBaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_route)
		
		val detail: Button = findViewById(R.id.detail)
		val vip: Button = findViewById(R.id.vip)
		val authentication: Button = findViewById(R.id.authentication)
		val unknown: Button = findViewById(R.id.unknown)
		
		detail.setOnClickListener {
			navigation("/profile/detail")
		}
		
		vip.setOnClickListener {
			navigation("/profile/vip")
		}
		
		authentication.setOnClickListener {
			navigation("/profile/authentication")
		}
		
		unknown.setOnClickListener {
			navigation("/profile/unknown")
		}
	}
	
	private fun navigation(url: String) {
		ARouter.getInstance().build(url).navigation()
	}
}