package com.cooder.project.common.ui.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/22 23:02:01
 *
 * 介绍：CoBaseActivity，已适配ViewBinding
 */
abstract class CoBaseActivity<VB : ViewBinding> : AppCompatActivity(), CoBaseActionInterface {
	
	private var _binding: VB? = null
	val binding: VB get() = _binding!!
	
	abstract fun getViewBinding(inflater: LayoutInflater): VB
	
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = getViewBinding(layoutInflater)
		setContentView(binding.root)
	}
	
	fun showToast(text: String?) {
		Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	fun showToast(@StringRes resId: Int) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
	}
	
	@JvmOverloads
	protected fun onBackPressed(resultCode: Int, intent: Intent? = null) {
		setResult(resultCode, intent)
		onBackPressedDispatcher.onBackPressed()
	}
	
	@JvmOverloads
	protected fun onBackPressedResultCanceled(intent: Intent? = null) {
		onBackPressed(Activity.RESULT_CANCELED, intent)
	}
	
	@JvmOverloads
	protected fun onBackPressedResultOk(intent: Intent? = null) {
		onBackPressed(Activity.RESULT_OK, intent)
	}
	
	@CallSuper
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
	
	fun startActivityForResultLauncher(intent: Intent, activityResultLauncher: ActivityResultLauncher<Intent>) {
		activityResultLauncher.launch(intent)
	}
}