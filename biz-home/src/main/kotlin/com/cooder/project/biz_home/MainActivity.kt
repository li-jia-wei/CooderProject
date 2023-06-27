package com.cooder.project.biz_home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.cooder.library.library.log.CoLog
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
		activityLogic = MainActivityLogic(binding, this, savedInstanceState)
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
	
	private var lastVolumeDownTime = 0L
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (BuildConfig.DEBUG) {
			val time = System.currentTimeMillis()
			if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
				if (time - lastVolumeDownTime <= 600) {
					// 音量下键点击事件
					try {
						val clazz: Class<*> = Class.forName("com.cooder.project.debug.tool.CoDebugToolDialogFragment")
						val target = clazz.getConstructor().newInstance() as DialogFragment
						target.show(supportFragmentManager, "debug_tool")
					} catch (e: Exception) {
						e.printStackTrace()
						CoLog.i(e.message)
					}
				}
			}
			lastVolumeDownTime = time
		}
		return super.onKeyDown(keyCode, event)
	}
}