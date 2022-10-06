package com.cooder.cooder.project.common.ui.component

import android.app.Application
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogManager
import com.cooder.cooder.library.log.printer.CooderConsolePrinter

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:17
 *
 * 文件介绍：CooderBaseApplication
 */
open class CooderBaseApplication : Application() {
	
	override fun onCreate() {
		super.onCreate()
		initCooderLog()
	}
	
	private fun initCooderLog() {
		CooderLogManager.init(this, object : CooderLogConfig() {
			override fun enable(): Boolean {
				return true
			}
			
			override fun includeStackTrack(): Boolean {
				return false
			}
			
			override fun includeThread(): Boolean {
				return false
			}
		}, CooderConsolePrinter())
	}
}