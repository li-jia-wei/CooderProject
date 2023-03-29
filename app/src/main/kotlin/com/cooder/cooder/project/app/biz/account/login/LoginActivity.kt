package com.cooder.cooder.project.app.biz.account.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.biz.account.AccountManager
import com.cooder.cooder.project.app.biz.account.register.RegisterActivity
import com.cooder.cooder.project.app.databinding.ActivityLoginBinding
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：AuthenticationActivity
 */
@Route(path = RoutePath.ACTIVITY_BIZ_ACCOUNT_LOGIN)
class LoginActivity : CoBaseActivity<ActivityLoginBinding>() {
	
	private val viewModel by lazy {
		ViewModelProvider(this)[LoginViewModel::class.java]
	}
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
		return ActivityLoginBinding.inflate(layoutInflater)
	}
	
	override fun onCreateActivity(savedInstanceState: Bundle?) {
		setStatusBar(true, Color.WHITE)
		
		loginObserver()
		
		binding.actionBack.setOnClickListener {
			onBackPressed(Activity.RESULT_CANCELED)
		}
		
		binding.actionRegister.setOnClickListener {
			goRegister()
		}
		
		binding.actionLogin.setOnClickListener {
			goLogin()
		}
	}
	
	private fun goLogin() {
		val username = binding.username.getText()
		val password = binding.password.getText()
		if (username.isEmpty()) {
			showToast(R.string.login_please_input_username)
			return
		}
		if (password.isEmpty()) {
			showToast(R.string.login_please_input_password)
			return
		}
		login(username, password)
	}
	
	private fun login(username: String, password: String) {
		viewModel.login(LoginViewModel.LoginMo(username, password))
	}
	
	private fun loginObserver() {
		viewModel.loginLiveData.observe(this) {
			if (it.isSuccessful()) {
				showToast(R.string.login_success)
				AccountManager.loginSuccess(it.data!!)
				onBackPressed(Activity.RESULT_OK)
			} else {
				binding.password.setText("")
				showToast(getString(R.string.login_failure, it.msg))
			}
		}
	}
	
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == RegisterActivity.REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK && data != null) {
			// 将注册成功后的账号返回到登录页的用户名
			val username = data.getStringExtra("username")
			if (username.isNullOrEmpty()) {
				binding.username.setText(username!!)
				binding.password.setText("")
			}
		}
	}
	
	/**
	 * 前往注册
	 */
	private fun goRegister() {
		CoRoute.startActivity(
			destination = RoutePath.ACTIVITY_BIZ_ACCOUNT_REGISTER,
			context = this,
			requestCode = RegisterActivity.REQUEST_CODE_REGISTER,
			greenChannel = true
		)
	}
}