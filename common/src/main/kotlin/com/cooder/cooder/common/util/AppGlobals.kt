package com.cooder.cooder.common.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

object AppGlobals {
	
	private var application: Application? = null
	
	/**
	 * 获取Application
	 */
	@SuppressLint("PrivateApi")
	fun getApplication(): Application {
		try {
			if (application == null) {
				application = Class.forName("android.app.ActivityThread")
					.getMethod("currentApplication")
					.invoke(null) as Application
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return application ?: throw IllegalStateException("Failed to get application!")
	}
	
	/**
	 * 获取BaseContext
	 */
	fun getBaseContext(): Context {
		if (application != null)
			return application!!.baseContext
		return getApplication().baseContext ?: throw IllegalStateException("Failed to get application!")
	}
}