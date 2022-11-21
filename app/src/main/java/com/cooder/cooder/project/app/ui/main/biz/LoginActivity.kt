package com.cooder.cooder.project.app.ui.main.biz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.ui.main.route.RouteFlag
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity
import com.cooder.cooder.project.common.ui.view.input.InputItemLayout

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：AuthenticationActivity
 */
@Route(path = "/profile/login", extras = RouteFlag.FLAG_LOGIN)
class LoginActivity : CooderBaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		
		val actionBack: TextView = findViewById(R.id.action_back)
		actionBack.setOnClickListener {
			finish()
		}
		
		val actionRegister: TextView = findViewById(R.id.action_register)
		actionRegister.setOnClickListener {
		
		}
		
		val actionLogin: Button = findViewById(R.id.action_login)
		actionLogin.setOnClickListener {
			goLogin()
		}
	}
	
	private fun goLogin() {
		val username = findViewById<InputItemLayout>(R.id.input_item_username).getText()
		val password = findViewById<InputItemLayout>(R.id.input_item_password).getText()
		if (username.isBlank()) {
//			Toast.makeText(this, "用户名不能")
		}
	}
}