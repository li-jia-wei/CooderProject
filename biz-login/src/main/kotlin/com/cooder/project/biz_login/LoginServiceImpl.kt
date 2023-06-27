package com.cooder.project.biz_login

import android.content.Context
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.cooder.project.common.route.RoutePath
import com.cooder.project.service_login.ILoginService

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/23 18:11
 *
 * 介绍：LoginServiceImpl
 */
@Route(path = RoutePath.BizLogin.SERVICE_LOGIN)
class LoginServiceImpl : ILoginService, IProvider {
	
	override fun init(context: Context?) {
	
	}
	
	override fun toLogin(context: Context, observer: Observer<Boolean>) {
		AccountManager.toLogin(context, observer)
	}
	
	override fun loginSuccessObserver(context: Context, observer: Observer<Boolean>) {
		AccountManager.loginSuccessObserver(context, observer)
	}
	
	override fun loginSuccess(boardingPass: String) {
		AccountManager.loginSuccess(boardingPass)
	}
	
	override fun getBoardingPass(): String {
		return AccountManager.getBoardingPass()
	}
	
	override fun isLogin(): Boolean {
		return AccountManager.isLogin()
	}
	
	override fun isNotLogin(): Boolean {
		return AccountManager.isNotLogin()
	}
}