package com.cooder.cooder.service_login

import android.content.Context
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/23 18:13
 *
 * 介绍：登录服务持有者
 */
object LoginServiceProvider {

    private const val SERVICE_LOGIN = "/biz_login/login/service"

    private val iLoginService = ARouter.getInstance().build(SERVICE_LOGIN).navigation() as? ILoginService

    fun toLogin(context: Context, observer: Observer<Boolean>) {
        iLoginService?.toLogin(context, observer)
    }

    fun loginSuccessObserver(context: Context, observer: Observer<Boolean>) {
        iLoginService?.loginSuccessObserver(context, observer)
    }

    fun loginSuccess(boardingPass: String) {
        iLoginService?.loginSuccess(boardingPass)
    }

    fun getBoardingPass(): String {
        return iLoginService?.getBoardingPass() ?: ""
    }

    fun isLogin(): Boolean {
        return iLoginService?.isLogin() == true
    }

    fun isNotLogin(): Boolean {
        return iLoginService?.isNotLogin() == true
    }
}