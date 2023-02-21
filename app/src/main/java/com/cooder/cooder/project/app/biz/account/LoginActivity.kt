package com.cooder.cooder.project.app.biz.account

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.log.CoLog
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.ActivityLoginBinding
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.AccountApi
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.cooder.project.common.ui.view.input.InputItemLayout
import com.cooder.cooder.project.common.util.PreferencesUtil

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：AuthenticationActivity
 */
@Route(path = RoutePath.ACTIVITY_ACCOUNT_LOGIN)
class LoginActivity : CoBaseActivity() {
	
	private lateinit var binding: ActivityLoginBinding
	
	companion object {
		const val REQUEST_CODE_LOGIN = 1000
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		setStatusBar(true, Color.WHITE)
		
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
		val username = binding.inputItemUsername.getText()
		val password = binding.inputItemPassword.getText()
		if (username.isEmpty()) {
			showToast(R.string.login_please_input_username)
			return
		}
		if (password.isEmpty()) {
			showToast(R.string.login_please_input_password)
			return
		}
		ApiFactory.create(AccountApi::class.java).login(username, password).enqueue(object : CoCallback<String> {
			override fun onSuccess(response: CoResponse<String>) {
				if (response.isSuccessful()) {
					showToast(R.string.login_success)
					val data: String? = response.data
					
					// user manager
					PreferencesUtil.putString("boarding-pass", data)
					onBackPressed(Activity.RESULT_OK)
				} else {
					showToast(getString(R.string.login_failure, response.message))
					binding.inputItemPassword.setText("")
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				CoLog.e(throwable.message)
				showToast(getString(R.string.login_failure, throwable.message))
				binding.inputItemPassword.setText("")
			}
		})
	}
	
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == RegisterActivity.REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK && data != null) {
			// 将注册成功后的账号返回到登录页的用户名
			val username = data.getStringExtra("username")
			if (username != null && username.isNotEmpty()) {
				val inputItemUsername: InputItemLayout = findViewById(R.id.input_item_username)
				val inputItemPassword: InputItemLayout = findViewById(R.id.input_item_password)
				inputItemUsername.setText(username)
				inputItemPassword.setText("")
			}
		}
	}
	
	/**
	 * 前往注册
	 */
	private fun goRegister() {
		ARouter.getInstance()
			.build(RoutePath.ACTIVITY_ACCOUNT_REGISTER)
			.greenChannel()
			.navigation(this, RegisterActivity.REQUEST_CODE_REGISTER)
	}
}