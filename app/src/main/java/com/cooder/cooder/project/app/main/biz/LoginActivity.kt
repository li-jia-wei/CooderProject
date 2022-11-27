package com.cooder.cooder.project.app.main.biz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.restful.CooderCallback
import com.cooder.cooder.library.restful.CooderResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.http.ApiFactory
import com.cooder.cooder.project.app.main.http.api.AccountApi
import com.cooder.cooder.project.app.main.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity
import com.cooder.cooder.project.common.ui.view.IconFontTextView
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
class LoginActivity : CooderBaseActivity() {
	
	private companion object {
		private const val REQUEST_CODE_REGISTER = 1000
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		
		val actionBack: IconFontTextView = findViewById(R.id.action_back)
		actionBack.setOnClickListener {
			finish()
		}
		
		val actionRegister: TextView = findViewById(R.id.action_register)
		actionRegister.setOnClickListener {
			goRegister()
		}
		
		val actionLogin: Button = findViewById(R.id.action_login)
		actionLogin.setOnClickListener {
			goLogin()
		}
	}
	
	private fun goLogin() {
		val inputItemUsername: InputItemLayout = findViewById(R.id.input_item_username)
		val inputItemPassword: InputItemLayout = findViewById(R.id.input_item_password)
		val username = inputItemUsername.getText()
		val password = inputItemPassword.getText()
		CooderLog.i(username, password)
		if (username.isEmpty()) {
			showToast(R.string.login_please_input_username)
			return
		}
		if (password.isEmpty()) {
			showToast(R.string.login_please_input_password)
			return
		}
		ApiFactory.create(AccountApi::class.java).login(username, password).enqueue(object : CooderCallback<String> {
			override fun onSuccess(response: CooderResponse<String>) {
				CooderLog.i(response.code)
				if (response.code == CooderResponse.SUCCESS) {
					showToast(R.string.login_success)
					val data: String? = response.data
					
					// user manager
					PreferencesUtil.putString("boarding-pass", data)
					finishWithResultOk(Intent())
					// TODO: 登录后的页面
				} else {
					showToast(getString(R.string.login_failure, response.msg))
					inputItemPassword.setText("")
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				CooderLog.e(throwable.message)
				showToast(getString(R.string.login_failure, throwable.message))
				inputItemPassword.setText("")
			}
		})
	}
	
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK && data != null) {
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
			.navigation(this, REQUEST_CODE_REGISTER)
	}
}