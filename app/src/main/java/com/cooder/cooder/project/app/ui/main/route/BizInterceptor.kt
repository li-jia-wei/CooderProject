package com.cooder.cooder.project.app.ui.main.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

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
	
	private val handler = Handler(Looper.getMainLooper()!!)
	
	private var context: Context? = null
	
	override fun init(context: Context?) {
		this.context = context
	}
	
	override fun process(postcard: Postcard, callback: InterceptorCallback?) {
		val flag = postcard.extra
		if (flag and RouteFlag.FLAG_LOGIN != 0) {
			// login
			showToast("请先登录")
			callback?.onInterrupt(RuntimeException("Need login."))
		} else if (flag and RouteFlag.FLAG_AUTHENTICATION != 0) {
			showToast("请先实名认证")
			callback?.onInterrupt(RuntimeException("Need Authentication."))
		} else if (flag and RouteFlag.FLAG_VIP != 0) {
			showToast("请先加入会员")
			callback?.onInterrupt(RuntimeException("Need VIP."))
		}
	}
	
	private fun showToast(message: String) {
		handler.post {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
		}
	}
}