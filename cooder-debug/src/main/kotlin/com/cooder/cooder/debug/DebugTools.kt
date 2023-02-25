package com.cooder.cooder.debug

import android.content.Intent
import android.os.Process
import com.cooder.cooder.project.common.util.AppGlobals
import com.cooder.cooder.project.common.util.PreferencesUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 01:07
 *
 * 介绍：DebugTools
 */
class DebugTools {
	
	/**
	 * 构建版本名称
	 */
	fun buildVersionName(): String {
		// 版本名称：2.0.5
		return "构建版本名称：${BuildConfig.VERSION_NAME}"
	}
	
	/**
	 * 构建版本号
	 */
	fun buildVersionCode(): String {
		return "构建版本号：${BuildConfig.VERSION_CODE}"
	}
	
	/**
	 * 构建时间
	 */
	fun buildTime(): String {
		return "构建时间：${BuildConfig.BUILD_TIME}"
	}
	
	/**
	 * 构建日期
	 */
	fun buildDate(): String {
		return "构建日期：${BuildConfig.BUILD_TIME}"
	}
	
	/**
	 * 构建环境
	 * @return debug or release
	 */
	fun buildEnvironment(): String {
		val environment = when (BuildConfig.BUILD_TYPE) {
			"debug" -> "测试环境"
			"release" -> "正式环境"
			else -> BuildConfig.BUILD_TYPE
		}
		return environment
	}
	
	/**
	 * 降级成http
	 */
	@CoDebug(name = "开启https降级", desc = "降级成http，可以使用抓包工具明文抓包")
	fun degradeToHttp() {
		PreferencesUtil.putBoolean("degrade_http", true)
		val context = AppGlobals.getBaseContext()
		val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
		intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		context.startActivity(intent)
		// 把自己干掉
		Process.killProcess(Process.myPid())
	}
}