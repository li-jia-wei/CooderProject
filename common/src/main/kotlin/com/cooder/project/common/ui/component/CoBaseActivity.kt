package com.cooder.project.common.ui.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.cooder.library.library.log.CoLog
import com.cooder.project.common.BuildConfig

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
	protected val binding: VB get() = _binding!!
	
	private var toast: Toast? = null
	
	abstract fun getViewBinding(inflater: LayoutInflater): VB
	
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = getViewBinding(layoutInflater)
		setContentView(binding.root)
	}
	
	protected fun showToast(text: String?) {
		if (toast == null) {
			toast = Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT)
		} else {
			toast!!.setText(text ?: "null")
		}
		toast!!.show()
	}
	
	protected fun showToast(@StringRes resId: Int, vararg args: Any?) {
		if (toast == null) {
			toast = Toast.makeText(this, getString(resId, *args), Toast.LENGTH_SHORT)
		} else {
			toast!!.setText(getString(resId, *args))
		}
		toast!!.show()
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
	
	private var lastVolumeDownTime = 0L
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (BuildConfig.DEBUG) {
			val time = System.currentTimeMillis()
			if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
				if (time - lastVolumeDownTime <= 600) {
					// 音量下键点击事件
					try {
						val clazz: Class<*> = Class.forName("com.cooder.project.debug.DebugToolDialogFragment")
						val target = clazz.getConstructor().newInstance() as DialogFragment
						target.showNow(supportFragmentManager, "DebugToolDialogFragmentTag")
					} catch (e: Exception) {
						CoLog.i(e)
					}
				}
			}
			lastVolumeDownTime = time
		}
		return super.onKeyDown(keyCode, event)
	}
}