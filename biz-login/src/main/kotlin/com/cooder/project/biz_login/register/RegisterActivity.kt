package com.cooder.project.biz_login.register

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.library.library.util.expends.setStatusBar
import com.cooder.project.biz_login.R
import com.cooder.project.biz_login.databinding.ActivityRegisterBinding
import com.cooder.project.common.route.RoutePath
import com.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 19:56
 *
 * 介绍：RegisterActivity
 */
@Route(path = RoutePath.BizLogin.ACTIVITY_REGISTER)
class RegisterActivity : CoBaseActivity<ActivityRegisterBinding>() {
	
	companion object {
		const val REQUEST_CODE_REGISTER = 1001
	}
	
	private val viewModel by lazy {
		ViewModelProvider(this)[RegisterViewModel::class.java]
	}
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityRegisterBinding {
		return ActivityRegisterBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStatusBar(true, Color.WHITE)
		
		binding.navigationBar.setNavigationListener {
			onBackPressed(Activity.RESULT_CANCELED)
		}
		
		binding.actionSubmit.setOnClickListener {
			goRegister()
		}
	}
	
	private fun goRegister() {
		
		val courseOrderId = binding.courseOrderId.getText()
		if (courseOrderId.length < 4) {
			showToast(R.string.register_please_input_course_order_id_last_4_digits)
			return
		}
		val moocId = binding.moocId.getText()
		if (moocId.isEmpty()) {
			showToast(R.string.register_please_input_mooc_id)
			return
		}
		val username = binding.username.getText()
		if (username.length < 6) {
			showToast(R.string.register_username_last_6_digits)
			return
		}
		val password = binding.password.getText()
		if (password.length < 6) {
			showToast(R.string.register_password_last_6_digits)
			return
		}
		val confirmPassword = binding.confirmPassword.getText()
		if (password != confirmPassword) {
			showToast(R.string.register_confirm_password_failure)
			binding.confirmPassword.setText("")
			return
		}
		
		register(username, password, moocId, courseOrderId)
	}
	
	/**
	 * 注册
	 */
	private fun register(username: String, password: String, moocId: String, courseOrderId: String) {
		viewModel.register(username, password, moocId, courseOrderId).observe(this) {
			if (it.success) {
				val intent = Intent()
				intent.putExtra("username", it.username)
				onBackPressed(Activity.RESULT_OK, intent)
			} else {
				showToast(getString(R.string.register_failure, it.result.msg))
				binding.password.setText("")
				binding.confirmPassword.setText("")
			}
		}
	}
}