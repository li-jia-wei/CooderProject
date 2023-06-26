package com.cooder.cooder.common.util

import android.content.Context
import android.content.SharedPreferences
import com.cooder.cooder.library.util.AppGlobals

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 18:32
 *
 * 介绍：共享配置工具
 */
object SPUtil {

    private const val CACHE_FILE = "cache_file"

    @JvmStatic
    fun putString(key: String, value: String?, safe: Boolean = false) {
        val v = if (safe && value != null) SafeUtil.encode(key, value) else value
        getPreferences()?.edit()?.putString(key, v)?.apply()
    }

    @JvmStatic
    @JvmOverloads
    fun getString(key: String, defValue: String? = null, safe: Boolean = false): String? {
        val str = getPreferences()?.getString(key, defValue)
        if (str == null || str == "null") return null
        return if (safe) SafeUtil.decode(key, str) else str
    }

    @JvmStatic
    fun putInt(key: String, value: Int) {
        getPreferences()?.edit()?.putInt(key, value)?.apply()
    }

    @JvmStatic
    @JvmOverloads
    fun getInt(key: String, defValue: Int = 0): Int {
        return getPreferences()?.getInt(key, defValue) ?: defValue
    }

    @JvmStatic
    fun putBoolean(key: String, value: Boolean) {
        getPreferences()?.edit()?.putBoolean(key, value)?.apply()
    }

    @JvmStatic
    @JvmOverloads
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return getPreferences()?.getBoolean(key, defValue) ?: defValue
    }

    @JvmStatic
    fun putFloat(key: String, value: Float) {
        getPreferences()?.edit()?.putFloat(key, value)?.apply()
    }

    @JvmStatic
    @JvmOverloads
    fun getFloat(key: String, defValue: Float = 0F): Float {
        return getPreferences()?.getFloat(key, defValue) ?: defValue
    }

    @JvmStatic
    fun putLong(key: String, value: Long) {
        getPreferences()?.edit()?.putLong(key, value)?.apply()
    }

    @JvmStatic
    @JvmOverloads
    fun getLong(key: String, defValue: Long = 0L): Long {
        return getPreferences()?.getLong(key, defValue) ?: defValue
    }

    @JvmStatic
    fun delete() {
        val application = AppGlobals.getApplication()
        application.deleteSharedPreferences(CACHE_FILE)
    }

    @JvmStatic
    private fun getPreferences(): SharedPreferences? {
        val application = AppGlobals.getApplication()
        return application.getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
    }
}