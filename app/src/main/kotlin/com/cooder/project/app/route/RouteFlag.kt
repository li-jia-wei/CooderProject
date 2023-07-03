package com.cooder.project.app.route

/**
 * 为目标页扩展属性
 */
object RouteFlag {
	/**
	 * 登陆
	 */
	const val FLAG_LOGIN = 0x01
	
	/**
	 * 实名认证
	 */
	const val FLAG_AUTHENTICATION = FLAG_LOGIN shl 1
	
	/**
	 * 成为会员
	 */
	const val FLAG_VIP = FLAG_AUTHENTICATION shl 1
}
