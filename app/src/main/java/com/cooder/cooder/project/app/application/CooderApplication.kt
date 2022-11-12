package com.cooder.cooder.project.app.application

import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogManager
import com.cooder.cooder.library.log.printer.CooderConsolePrinter
import com.cooder.cooder.project.app.BuildConfig
import com.cooder.cooder.project.common.ui.component.CooderBaseApplication

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:26
 *
 * 文件介绍：CooderApplication
 */
class CooderApplication : CooderBaseApplication() {
	
	override fun onCreate() {
		super.onCreate()
		initCooderLog()
		initARouter()
	}
	
	private fun initCooderLog() {
		CooderLogManager.init(this, object : CooderLogConfig() {
			override fun enable(): Boolean {
				return true
			}
			
			override fun globalTag(): String {
				return "Cooder"
			}
			
			override fun includeThread(): Boolean {
				return false
			}
			
			override fun includeStackTrack(): Boolean {
				return false
			}
			
			override fun stackTrackDepth(): Int {
				return 10
			}
		}, CooderConsolePrinter())
	}
	
	private fun initARouter() {
		if (BuildConfig.DEBUG) {
			ARouter.openLog()
			ARouter.openDebug()
		}
		ARouter.init(this)
	}
}