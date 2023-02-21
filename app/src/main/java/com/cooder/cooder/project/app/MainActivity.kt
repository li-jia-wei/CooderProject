package com.cooder.cooder.project.app

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.project.app.databinding.ActivityMainBinding
import com.cooder.cooder.project.app.logic.MainActivityLogic
import com.cooder.cooder.project.app.logic.MainActivityLogic.ActivityProvider
import com.cooder.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 12:20
 *
 * 介绍：MainActivity
 */
class MainActivity : CoBaseActivity(), ActivityProvider {
	
	private lateinit var activityLogic: MainActivityLogic
	private lateinit var binding: ActivityMainBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		activityLogic = MainActivityLogic(this, savedInstanceState)
		
		// 设置状态栏
		setStatusBar(true, Color.WHITE)
	}
	
	override fun getContext(): Context {
		return this
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		activityLogic.onSaveInstanceState(outState)
	}
	
	@Deprecated("Deprecated in Java")
	override fun startActivityForResult(intent: Intent?, requestCode: Int) {
		@Suppress("DEPRECATION")
		super.startActivityForResult(intent, requestCode)
		val fragments = supportFragmentManager.fragments
		for (fragment in fragments) {
			fragment.startActivityForResult(intent, requestCode)
		}
	}
	
	/**
	 * 将result发放给Fragment
	 */
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		for (fragment in supportFragmentManager.fragments) {
			fragment.onActivityResult(requestCode, resultCode, data)
		}
	}
	
	fun getTabBottomLayoutHeight(): Float {
		return activityLogic.getTabBottomLayoutHeight()
	}
}