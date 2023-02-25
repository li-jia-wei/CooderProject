package com.cooder.cooder.project.app

import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.log.CoLogConfig
import com.cooder.cooder.library.log.CoLogManager
import com.cooder.cooder.library.log.printer.CoConsolePrinter
import com.cooder.cooder.project.common.ui.component.CoBaseApplication

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 14:26
 *
 * 介绍：CoApplication
 */
class CoApplication : CoBaseApplication() {
	
	override fun onCreate() {
		super.onCreate()
		initCoLog()
		initARouter()
	}
	
	private fun initCoLog() {
		CoLogManager.init(this, object : CoLogConfig() {
			override fun enable(): Boolean {
				return true
			}
			
			override fun globalTag(): String {
				return "CooderProjectTag"
			}
		}, CoConsolePrinter())
	}
	
	private fun initARouter() {
		if (BuildConfig.DEBUG) {
			ARouter.openLog()
			ARouter.openDebug()
		}
		ARouter.init(this)
	}
}