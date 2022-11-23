package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.library.log.CooderLog

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:16
 *
 * 文件介绍：CooderBaseActivity
 */
open class CooderBaseActivity : AppCompatActivity(), CooderBaseActionInterface {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	protected fun showToast(text: String?) {
		CooderLog.i(text)
		Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	protected fun showToast(@StringRes resId: Int) {
		CooderLog.i(getString(resId))
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
	}
}