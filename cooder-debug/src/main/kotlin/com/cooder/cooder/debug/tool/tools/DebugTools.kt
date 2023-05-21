package com.cooder.cooder.debug.tool.tools

import android.content.Intent
import android.os.Process
import com.cooder.cooder.debug.BuildConfig
import com.cooder.cooder.debug.tool.annotation.CoDebug
import com.cooder.cooder.debug.tool.annotation.CoOrder
import com.cooder.cooder.project.common.util.AppGlobals
import com.cooder.cooder.project.common.util.SPUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 01:07
 *
 * 介绍：Debug工具
 */
class DebugTools {
	
	/**
	 * 构建版本名称
	 */
	@CoOrder(order = 1)
	fun buildVersionName(): String {
		// 版本名称：2.0.5
		return "构建版本名称：${BuildConfig.VERSION_NAME}"
	}
	
	/**
	 * 构建版本号
	 */
	@CoOrder(order = 2)
	fun buildVersionCode(): String {
		return "构建版本号：${BuildConfig.VERSION_CODE}"
	}
	
	/**
	 * 构建时间
	 */
	@CoOrder(order = 3)
	fun buildTime(): String {
		return "构建时间：${BuildConfig.BUILD_TIME}"
	}
	
	/**
	 * 构建日期
	 */
	@CoOrder(order = 4)
	fun buildDate(): String {
		return "构建日期：${BuildConfig.BUILD_DATE}"
	}
	
	/**
	 * 构建环境
	 * @return debug or release
	 */
	@CoOrder(order = 5)
	fun buildEnvironment(): String {
		@Suppress("KotlinConstantConditions")
		val environment = when (BuildConfig.BUILD_TYPE) {
			"debug" -> "测试环境"
			"release" -> "正式环境"
			else -> BuildConfig.BUILD_TYPE
		}
		return "构建环境：$environment"
	}
	
	/**
	 * 降级成http
	 */
	@CoOrder(order = 6)
	@CoDebug(name = "开启https降级", hint = "重启", desc = "降级成http，可以使用抓包工具明文抓包")
	fun degradeToHttp() {
		SPUtil.putBoolean("degrade_http", true)
		val context = AppGlobals.getBaseContext()
		val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
		intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
		context.startActivity(intent)
		Process.killProcess(Process.myPid())
	}
}