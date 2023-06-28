package com.cooder.project.debug.tool.tools

import android.content.Intent
import android.os.Process
import android.widget.Toast
import com.cooder.library.library.cache.CoStorage
import com.cooder.library.library.log.CoLog
import com.cooder.library.library.util.AppGlobals
import com.cooder.project.debug.BuildConfig
import com.cooder.project.debug.tool.annotation.CoDebug
import com.cooder.project.debug.tool.annotation.CoOrder
import java.io.File

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
		return "构建环境：${BuildConfig.BUILD_TYPE}"
	}
	
	/**
	 * 降级成http
	 */
	@CoOrder(order = 6)
	@CoDebug(name = "开启https降级", hint = "重\n启", desc = "降级成http，可以使用抓包工具明文抓包")
	fun degradeToHttp() {
		val context = AppGlobals.getBaseContext()
		if (com.cooder.project.common.http.ApiFactory.degradeToHttp) {
			Toast.makeText(context, "已经降级成http了", Toast.LENGTH_SHORT).show()
			return
		}
		Toast.makeText(context, "正在重启app", Toast.LENGTH_SHORT).show()
		CoStorage.saveCache(com.cooder.project.common.http.ApiFactory.KEY_DEGRADE_HTTP, true)
		val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
		intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
		context.startActivity(intent)
		Process.killProcess(Process.myPid())
	}
	
	@CoOrder(order = 7)
	@CoDebug(name = "清理缓存", desc = "将清理应用缓存在内存中的数据")
	fun clearCache() {
		val context = AppGlobals.getBaseContext()
		val deleteCacheDirSuccess = deleteDir(context.cacheDir, true)
		val deleteCodeCacheDirSuccess = deleteDir(context.codeCacheDir, true)
		if (deleteCacheDirSuccess && deleteCodeCacheDirSuccess) {
			Toast.makeText(context, "已清理缓存", Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(context, "缓存清理失败", Toast.LENGTH_SHORT).show()
		}
	}
	
	@CoOrder(order = 8)
	@CoDebug(name = "清理储存", desc = "将清理用户存储在应用上的数据")
	fun clearData() {
		val context = AppGlobals.getBaseContext()
		val success = deleteDir(context.dataDir, false)
		if (success) {
			Toast.makeText(context, "已清理储存", Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(context, "储存清理失败", Toast.LENGTH_SHORT).show()
		}
	}
	
	private fun deleteDir(dir: File, include: Boolean): Boolean {
		if (!dir.exists() || !dir.isDirectory) {
			return false
		}
		dir.listFiles()?.forEach {
			if (it.isDirectory) {
				deleteDir(it, true)
			} else {
				val success = it.delete()
				CoLog.i("[文件] : ${if (success) "删除成功" else "删除失败"} : ${it.absolutePath}")
			}
		}
		if (include) {
			val success = dir.delete()
			CoLog.i("[目录] : ${if (success) "删除成功" else "删除失败"} : ${dir.absolutePath}")
		}
		return true
	}
}