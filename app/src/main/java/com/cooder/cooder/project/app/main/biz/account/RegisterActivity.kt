package com.cooder.cooder.project.app.main.biz.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.http.ApiFactory
import com.cooder.cooder.project.app.main.http.api.AccountApi
import com.cooder.cooder.project.app.main.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.cooder.project.common.ui.view.IconFontTextView
import com.cooder.cooder.project.common.ui.view.input.InputItemLayout
import com.google.android.material.button.MaterialButton

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 19:56
 *
 * 介绍：RegisterActivity
 */
@Route(path = RoutePath.ACTIVITY_ACCOUNT_REGISTER)
class RegisterActivity : CoBaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register)
		
		setStatusBar(true, Color.WHITE)
		
		val actionBack: IconFontTextView = findViewById(R.id.action_back)
		actionBack.setOnClickListener {
			finishWithResultCanceled()
		}
		
		val actionSubmit: MaterialButton = findViewById(R.id.action_submit)
		actionSubmit.setOnClickListener {
			goRegister()
		}
	}
	
	private fun goRegister() {
		val inputItemCourseOrderId: InputItemLayout = findViewById(R.id.input_item_course_order_id)
		val inputItemMoocId: InputItemLayout = findViewById(R.id.input_item_mooc_id)
		val inputItemUsername: InputItemLayout = findViewById(R.id.input_item_username)
		val inputItemPassword: InputItemLayout = findViewById(R.id.input_item_password)
		val inputItemConfirmPassword: InputItemLayout = findViewById(R.id.input_item_confirm_password)
		
		val courseOrderId = inputItemCourseOrderId.getText()
		if (courseOrderId.length < 4) {
			showToast(R.string.register_please_input_course_order_id_last_4_digits)
			return
		}
		val moocId = inputItemMoocId.getText()
		if (moocId.isEmpty()) {
			showToast(R.string.register_please_input_mooc_id)
			return
		}
		val username = inputItemUsername.getText()
		if (username.length < 6) {
			showToast(R.string.register_username_last_6_digits)
			return
		}
		val password = inputItemPassword.getText()
		if (password.length < 6) {
			showToast(R.string.register_password_last_6_digits)
			return
		}
		val confirmPassword = inputItemConfirmPassword.getText()
		if (password != confirmPassword) {
			showToast(R.string.register_confirm_password_failure)
			inputItemConfirmPassword.setText("")
			return
		}
		ApiFactory.create(AccountApi::class.java).register(username, password, moocId, courseOrderId).enqueue(object : CoCallback<String> {
			override fun onSuccess(response: CoResponse<String>) {
				if (response.isSuccessful()) {
					// 注册成功
					val intent = Intent()
					intent.putExtra("username", username)
					finishWithResultOk(intent)
				} else {
					showToast(getString(R.string.register_failure, response.message))
					inputItemPassword.setText("")
					inputItemConfirmPassword.setText("")
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				showToast(getString(R.string.register_failure, throwable.message))
				inputItemPassword.setText("")
				inputItemConfirmPassword.setText("")
			}
		})
	}
}