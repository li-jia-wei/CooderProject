package com.cooder.project.biz_home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import com.cooder.library.library.util.CoDisplayUtil
import com.cooder.library.library.util.expends.immersiveStatusBar
import com.cooder.library.library.util.expends.setStatusBar
import com.cooder.project.biz_home.databinding.ActivityMainBinding
import com.cooder.project.biz_home.logic.MainActivityLogic
import com.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 12:20
 *
 * 介绍：MainActivity
 */
class MainActivity : CoBaseActivity<ActivityMainBinding>(), MainActivityLogic.ActivityProvider {
	
	private lateinit var activityLogic: MainActivityLogic
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
		return ActivityMainBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStatusBar(true, Color.WHITE)
		immersiveStatusBar(true)
		
		activityLogic = MainActivityLogic(binding, this, savedInstanceState)
		
		val statusBarsHeight = CoDisplayUtil.getStatusBarsHeight(CoDisplayUtil.Unit.PX)
		binding.fragmentTabView.setPadding(0, statusBarsHeight, 0, 0)
	}
	
	override fun getContext(): Context {
		return this
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		activityLogic.onSaveInstanceState(outState)
	}
	
	fun getTabBottomLayoutHeight(): Float {
		return binding.tabBottomLayout.getTabBottomLayoutHeight()
	}
}