package com.cooder.cooder.common.route

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.common.R
import com.cooder.cooder.common.util.AppGlobals

object CoRoute {

    /**
     * 通过网址启动浏览器
     * @param url 指定的网址
     */
    fun startActivityForBrowser(url: String?) {
        val context = AppGlobals.getBaseContext()
        if (url.isNullOrEmpty()) {
            Toast.makeText(context, R.string.route_url_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        // 使用application context启动activity不会报错
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmOverloads
    fun startActivity(
        @RoutePath.ActivityRef destination: String,
        bundle: Bundle? = null,
        context: Context? = null,
        requestCode: Int = Int.MIN_VALUE,
        greenChannel: Boolean = false
    ) {
        val postcard: Postcard = ARouter.getInstance().build(destination)
        if (bundle != null) {
            postcard.with(bundle)
        }
        if (greenChannel) {
            postcard.greenChannel()
        }
        if (context == null) {
            postcard.navigation()
        } else {
            if (requestCode != Int.MIN_VALUE && context is Activity) {
                postcard.navigation(context, requestCode)
            } else {
                postcard.navigation(context)
            }
        }
    }

    fun <T> getService(@RoutePath.ServiceDef destination: String): T? {
        @Suppress("UNCHECKED_CAST")
        return ARouter.getInstance().build(destination).navigation() as? T
    }

    fun inject(target: Any) {
        ARouter.getInstance().inject(target)
    }
}