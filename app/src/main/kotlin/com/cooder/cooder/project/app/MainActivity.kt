package com.cooder.cooder.project.app

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
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
class MainActivity : CoBaseActivity<ActivityMainBinding>(), ActivityProvider {
	
	private lateinit var activityLogic: MainActivityLogic
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
		return ActivityMainBinding.inflate(inflater)
	}
	
	override fun onCreateActivity(savedInstanceState: Bundle?) {
		setStatusBar(true, Color.WHITE)
		activityLogic = MainActivityLogic(binding, this, savedInstanceState)
	}
	
	override fun getContext(): Context {
		return this
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		activityLogic.onSaveInstanceState(outState)
	}
	
	@Deprecated("Deprecated in Java")
	@Suppress("DEPRECATION")
	override fun startActivityForResult(intent: Intent, requestCode: Int) {
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
		return binding.tabBottomLayout.getTabBottomLayoutHeight()
	}
	
	private var lastVolumeDownTime = 0L
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		val time = System.currentTimeMillis()
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			if (time - lastVolumeDownTime <= 600) {
				// 音量下键点击事件
				if (BuildConfig.DEBUG) {
					try {
						val clazz: Class<*> = Class.forName("com.cooder.cooder.debug.tool.CoDebugToolDialogFragment")
						val target = clazz.getConstructor().newInstance() as DialogFragment
						target.show(supportFragmentManager, "debug_tool")
					} catch (e: Exception) {
						e.printStackTrace()
					}
				}
			}
		}
		lastVolumeDownTime = time
		return super.onKeyDown(keyCode, event)
	}
}