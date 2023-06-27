package com.cooder.project.common.util

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/11 14:46
 *
 * 介绍：仅支持 [0-9a-zA-Z_]
 */
object SafeUtil {
	
	private val chars = CharArray(94) { i -> (i + 33).toChar() }
	
	/**
	 * 编码
	 */
	fun encode(key: String, str: String): String {
		val sb = StringBuilder()
		for (i in str.indices) {
			val j = chars.indexOf(str[i])
			val k = (j + i * i + offset(key)) % chars.size
			sb.append(chars[k])
		}
		return sb.toString()
	}
	
	/**
	 * 解码
	 */
	fun decode(key: String, str: String): String {
		val sb = StringBuilder()
		for (i in str.indices) {
			val k = chars.indexOf(str[i])
			var j = (k - i * i - offset(key))
			while (j < 0) {
				j += chars.size
			}
			j %= chars.size
			sb.append(chars[j])
		}
		return sb.toString()
	}
	
	private fun offset(key: String): Int {
		var offset = 0
		for (c in key) {
			offset += chars.indexOf(c)
		}
		return offset % chars.size
	}
}