package com.cooder.project.service_login

import android.content.Context
import androidx.lifecycle.Observer

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/23 17:48
 *
 * 介绍：登录服务
 */
interface ILoginService {
	
	/**
	 * 登录
	 */
	fun toLogin(context: Context, observer: Observer<Boolean>)
	
	/**
	 * 登录成功回调
	 */
	fun loginSuccessObserver(context: Context, observer: Observer<Boolean>)
	
	/**
	 * 设置登录成功
	 */
	fun loginSuccess(boardingPass: String)
	
	/**
	 * 获取通行证
	 */
	fun getBoardingPass(): String
	
	/**
	 * 是否登录
	 */
	fun isLogin(): Boolean
	
	/**
	 * 是否未登录
	 */
	fun isNotLogin(): Boolean
}