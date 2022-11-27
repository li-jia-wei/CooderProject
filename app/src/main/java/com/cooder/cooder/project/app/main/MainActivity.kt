package com.cooder.cooder.project.app.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.biz.LoginActivity
import com.cooder.cooder.project.app.main.logic.MainActivityLogic
import com.cooder.cooder.project.app.main.logic.MainActivityLogic.ActivityProvider
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 12:20
 *
 * 介绍：MainActivity
 */
class MainActivity : CooderBaseActivity(), ActivityProvider {
	
	private lateinit var activityLogic: MainActivityLogic
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		// 去登录
		startActivity(Intent(this, LoginActivity::class.java))
		
		activityLogic = MainActivityLogic(this, savedInstanceState)
	}
	
	override fun getContext(): Context {
		return this
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		activityLogic.onSaveInstanceState(outState)
	}
}