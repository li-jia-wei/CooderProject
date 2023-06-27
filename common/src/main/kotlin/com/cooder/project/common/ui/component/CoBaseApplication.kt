package com.cooder.project.common.ui.component

import android.app.Application
import androidx.annotation.CallSuper

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 14:17
 *
 * 介绍：CoBaseApplication
 */
open class CoBaseApplication : Application() {
	
	@CallSuper
	override fun onCreate() {
		super.onCreate()
	}
}