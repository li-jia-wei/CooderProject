package com.cooder.cooder.project.app.main.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.common.util.AppGlobals

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:19
 *
 * 介绍：Biz拦截器
 */
@Interceptor(priority = 9, name = "interceptor_biz")
class BizInterceptor : IInterceptor {
	
	private val handler get() = Handler(Looper.getMainLooper()!!)
	
	private lateinit var context: Context
	
	override fun init(context: Context?) {
		this.context = context ?: AppGlobals.getBaseContext()
	}
	
	override fun process(postcard: Postcard, callback: InterceptorCallback) {
		val flag = postcard.extra
		if (flag.isInterceptByFlag(RouteFlag.FLAG_LOGIN)) {
			callback.onInterrupt(RuntimeException(context.getString(R.string.interceptor_please_login_first)))
			loginIntercept()
		} else {
			callback.onContinue(postcard)
		}
	}
	
	/**
	 * 登录拦截
	 */
	private fun loginIntercept() {
		handler.post {
			Toast.makeText(context, context.getString(R.string.interceptor_please_login_first), Toast.LENGTH_SHORT).show()
			ARouter.getInstance().build(RoutePath.ACTIVITY_ACCOUNT_LOGIN).navigation()
		}
	}
	
	/**
	 * 判断是否包含目标flag
	 */
	private fun Int.isInterceptByFlag(targetFlag: Int): Boolean {
		return this and targetFlag != 0
	}
}