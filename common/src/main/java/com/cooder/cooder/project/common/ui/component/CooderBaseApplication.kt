package com.cooder.cooder.project.common.ui.component

import android.app.Application
import androidx.annotation.CallSuper

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
	
	@CallSuper
	override fun onCreate() {
		super.onCreate()
	}
}