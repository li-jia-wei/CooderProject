package com.cooder.cooder.project.app.biz.account

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cooder.cooder.library.util.AppGlobals
import com.cooder.cooder.project.app.biz.account.login.LoginActivity
import com.cooder.cooder.project.common.util.SPUtil

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/8 16:06
 *
 * 介绍：账户管理
 */
object AccountManager {
	
	private const val KEY_BOARDING_PASS = "boarding-pass"
	
	private val loginLiveData = MutableLiveData<Boolean>()
	private val loginForeverObservers = mutableListOf<Observer<Boolean>>()
	
	private var boardingPass: String? = null
	
	/**
	 * 登录
	 */
	@JvmOverloads
	fun toLogin(context: Context = AppGlobals.getContext(), observer: Observer<Boolean>) {
		if (context is LifecycleOwner) {
			loginLiveData.observe(context, observer)
		} else {
			loginLiveData.observeForever(observer)
			loginForeverObservers += observer
		}
		val intent = Intent(context, LoginActivity::class.java)
		if (context is Application) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}
		context.startActivity(intent)
	}
	
	/**
	 * 登录成功回调
	 */
	fun loginSuccessObserver(
		context: Context = AppGlobals.getContext(),
		observer: Observer<Boolean>
	) {
		if (context is LifecycleOwner) {
			loginLiveData.observe(context, observer)
		} else {
			loginLiveData.observeForever(observer)
			loginForeverObservers += observer
		}
	}
	
	fun loginSuccess(boardingPass: String) {
		SPUtil.putString(KEY_BOARDING_PASS, boardingPass, true)
		this.boardingPass = boardingPass
		loginLiveData.value = true
		clearLoginForeverObservers()
	}
	
	private fun clearLoginForeverObservers() {
		for (observer in loginForeverObservers) {
			loginLiveData.removeObserver(observer)
		}
		loginForeverObservers.clear()
	}
	
	fun getBoardingPass(): String {
		if (!TextUtils.isEmpty(boardingPass)) {
			return boardingPass!!
		}
		boardingPass = SPUtil.getString(KEY_BOARDING_PASS, "", true)
		return boardingPass!!
	}
	
	/**
	 * 判断用户是否登录
	 */
	fun isLogin(): Boolean {
		return !TextUtils.isEmpty(boardingPass)
	}
	
	fun isNotLogin(): Boolean {
		return TextUtils.isEmpty(boardingPass)
	}
}