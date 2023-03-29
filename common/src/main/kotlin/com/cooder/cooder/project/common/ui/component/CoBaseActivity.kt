package com.cooder.cooder.project.common.ui.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.cooder.cooder.library.util.expends.checkOverrideMethod
import com.cooder.cooder.project.common.ui.component.annotation.AliveCount
import com.cooder.cooder.project.common.ui.component.annotation.AliveMultiple

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
	
	private val aliveChecker by lazy { AliveChecker(this::class.java) }
	
	/**
	 * 不要重写此方法，使用onCreateActivity
	 * @see onCreateActivity
	 */
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		checkOverrideMethod(this::class.java, "onCreate", Bundle::class.java)
		
		if (aliveChecker.canCreate()) {
			aliveChecker.plusOneAlive()
			_binding = getViewBinding(layoutInflater)
			setContentView(binding.root)
			onCreateActivity(savedInstanceState)
		} else {
			onBackPressed(Activity.RESULT_CANCELED)
		}
	}
	
	/**
	 * 请在此方法中初始化控件
	 */
	abstract fun onCreateActivity(savedInstanceState: Bundle?)
	
	fun showToast(text: String?) {
		Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	fun showToast(@StringRes resId: Int) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
	}
	
	@JvmOverloads
	protected fun onBackPressed(resultCode: Int, intent: Intent? = null) {
		if (intent != null) {
			setResult(resultCode, intent)
		} else {
			setResult(resultCode)
		}
		onBackPressedDispatcher.onBackPressed()
	}
	
	@Suppress("OVERRIDE_DEPRECATION")
	override fun onBackPressed() {
		onBackPressedDispatcher.onBackPressed()
	}
	
	@CallSuper
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
		aliveChecker.minusOneAlive()
	}
	
	/**
	 * 查看Activity是否能被创建起来
	 */
	private class AliveChecker(private val clazz: Class<out CoBaseActivity<*>>) {
		
		private companion object {
			private val ALIVE_COUNT = mutableMapOf<Class<out CoBaseActivity<*>>, Int>()
		}
		
		init {
			checkAnnotation()
		}
		
		private fun checkAnnotation() {
			val count = clazz.getAnnotation(AliveCount::class.java)
			val multiple = clazz.getAnnotation(AliveMultiple::class.java)
			var i = 0
			if (count != null) i++
			if (multiple != null) i++
			require(i <= 1) { "只能同时使用@AliveCount和@AliveMultiple中的一个" }
		}
		
		/**
		 * 获取最大存活数量
		 */
		private fun getMaxAliveCount(): Int {
			val aliveCount = javaClass.getAnnotation(AliveCount::class.java) ?: return 1
			return aliveCount.count
		}
		
		/**
		 * 获取是否能存在多个，默认最多只能存在一个
		 */
		private fun canAliveMultiple(): Boolean {
			val aliveMultiple = javaClass.getAnnotation(AliveMultiple::class.java)
			return aliveMultiple != null
		}
		
		/**
		 * 查看是否能创建
		 */
		fun canCreate(): Boolean {
			return synchronized(clazz) {
				if (canAliveMultiple()) {
					true
				} else {
					val count = getMaxAliveCount()
					val current = getCurrentCount()
					current < count
				}
			}
		}
		
		/**
		 * 添加一个存活Activity
		 */
		fun plusOneAlive() {
			synchronized(clazz) {
				val count = ALIVE_COUNT[clazz]
				if (count == null) {
					ALIVE_COUNT[clazz] = 1
				} else {
					ALIVE_COUNT[clazz] = count + 1
				}
			}
		}
		
		/**
		 * 减少一个存活Activity
		 */
		fun minusOneAlive() {
			synchronized(clazz) {
				val count = ALIVE_COUNT[clazz] ?: throw IllegalStateException("ALIVE_COUNT[$clazz] == null")
				ALIVE_COUNT[clazz] = count - 1
			}
		}
		
		private fun getCurrentCount(): Int {
			return ALIVE_COUNT[clazz] ?: 0
		}
	}
}