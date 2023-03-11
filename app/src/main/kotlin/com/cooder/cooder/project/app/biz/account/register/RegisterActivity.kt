package com.cooder.cooder.project.app.biz.account.register

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.ActivityRegisterBinding
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 19:56
 *
 * 介绍：RegisterActivity
 */
@Route(path = RoutePath.ACTIVITY_BIZ_ACCOUNT_REGISTER)
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
		
		binding.actionBack.setOnClickListener {
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
		
		viewModel.register(username, password, moocId, courseOrderId).observe(this) {
			if (it == RegisterViewModel.LOGIN_SUCCESS) {
				val intent = Intent()
				intent.putExtra("username", username)
				onBackPressed(Activity.RESULT_OK, intent)
			} else {
				showToast(getString(R.string.register_failure, it))
				binding.password.setText("")
				binding.confirmPassword.setText("")
			}
		}
	}
}