package com.cooder.cooder.project.common.ui.component

import android.app.Activity
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 14:16
 *
 * 介绍：CoBaseActivity
 */
abstract class CoBaseActivity : AppCompatActivity(), CoBaseActionInterface {
	
	private val viewCaches = SparseArray<View>()
	
	private var onBackInvokedCallback: OnBackInvokedCallback? = null
	
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
			onBackInvokedCallback = OnBackInvokedCallback {
				// ...
			}
		}
	}
	
	protected fun showToast(text: String?) {
		Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	protected fun showToast(@StringRes resId: Int) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
	}
	
	/**
	 * 以 Ok 结果返回
	 */
	@JvmOverloads
	protected fun finishWithResultOk(intent: Intent? = null) {
		finishWithResult(Activity.RESULT_OK, intent)
	}
	
	/**
	 * 以 Canceled 结果返回
	 */
	@JvmOverloads
	protected fun finishWithResultCanceled(intent: Intent? = null) {
		finishWithResult(Activity.RESULT_CANCELED, intent)
	}
	
	/**
	 * 以 FirstUser 结果返回
	 */
	@JvmOverloads
	protected fun finishWithResultFirstUser(intent: Intent? = null) {
		finishWithResult(Activity.RESULT_FIRST_USER, intent)
	}
	
	private fun finishWithResult(resultCode: Int, intent: Intent?) {
		if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
			onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, onBackInvokedCallback!!)
		}
		// 执行返回操作
		onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				setResult(resultCode, intent)
				finish()
			}
		})
		onBackPressedDispatcher.onBackPressed()
	}
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : View> findViewById(id: Int): T {
		var view = viewCaches[id]
		if (view == null) {
			view = super.findViewById(id)
			viewCaches[id] = view
		}
		return view as T
	}
	
	@CallSuper
	override fun onDestroy() {
		super.onDestroy()
		if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
			onBackInvokedCallback?.let {
				onBackInvokedDispatcher.unregisterOnBackInvokedCallback(it)
			}
		}
	}
}