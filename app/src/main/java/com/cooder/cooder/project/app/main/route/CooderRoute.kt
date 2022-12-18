package com.cooder.cooder.project.app.main.route

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.common.util.AppGlobals

object CooderRoute {
	
	/**
	 * 通过网址启动浏览器
	 * @param url 指定的网址
	 */
	fun startActivityForBrowser(url: String?) {
		val context = AppGlobals.getBaseContext()
		if (url == null || url.isEmpty()) {
			Toast.makeText(context, R.string.route_url_is_empty, Toast.LENGTH_SHORT).show()
			return
		}
		val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
		// 防止在部分机型上面启动不了浏览器，比如：华为
		intent.addCategory(Intent.CATEGORY_BROWSABLE)
		// 使用application context启动activity不会报错
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		context.startActivity(intent)
	}
}