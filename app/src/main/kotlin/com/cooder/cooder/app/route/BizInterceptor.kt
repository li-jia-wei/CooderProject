package com.cooder.cooder.app.route

import android.content.Context
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.cooder.cooder.library.util.CoMainHandler
import com.cooder.cooder.service_login.LoginServiceProvider

/**
 * 业务的拦截器，判断目标页是否具备预先定义好的属性
 * @see RouteFlag
 */
@Interceptor(name = "biz_interceptor", priority = 9)
open class BizInterceptor : IInterceptor {
    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    /**
     * note:该方法运行在ARouter的线程池中
     */
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        if ((flag and (RouteFlag.FLAG_LOGIN) != 0)) {
            //login
            loginIfNecessary(postcard, callback)
        } else {
            callback!!.onContinue(postcard)
        }
    }

    /**
     * 登录
     */
    private fun loginIfNecessary(postcard: Postcard?, callback: InterceptorCallback?) {
        CoMainHandler.post {
            if (LoginServiceProvider.isLogin()) {
                callback?.onContinue(postcard)
            } else {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
                LoginServiceProvider.toLogin(context!!) {
                    callback?.onContinue(postcard)
                }
            }
        }
    }
}