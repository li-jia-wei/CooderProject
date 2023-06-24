package com.cooder.cooder.biz_login.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.biz_login.R
import com.cooder.cooder.biz_login.databinding.ActivityLoginBinding
import com.cooder.cooder.biz_login.register.RegisterActivity
import com.cooder.cooder.common.route.RoutePath
import com.cooder.cooder.common.ui.component.CoBaseActivity
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.service_login.LoginServiceProvider

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 19:51
 *
 * 介绍：AuthenticationActivity
 */
@Route(path = RoutePath.BizLogin.ACTIVITY_LOGIN)
class LoginActivity : CoBaseActivity<ActivityLoginBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setStatusBar(true, Color.WHITE)

        binding.actionBack.setOnClickListener {
            onBackPressed(Activity.RESULT_CANCELED)
        }

        binding.actionRegister.setOnClickListener {
            toRegister()
        }

        binding.actionLogin.setOnClickListener {
            toLogin()
        }
    }

    private fun toLogin() {
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
        viewModel.login(LoginViewModel.LoginMo(username, password)).observe(this) {
            if (it.isSuccessful()) {
                showToast(R.string.login_success)
                LoginServiceProvider.loginSuccess(it.data!!)
                onBackPressed(Activity.RESULT_OK)
            } else {
                binding.password.setText("")
                showToast(getString(R.string.login_failure, it.msg))
            }
        }
    }

    /**
     * 注册页结果启动器
     */
    private val registerActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RegisterActivity.REQUEST_CODE_REGISTER && it.resultCode == Activity.RESULT_OK && it.data != null) {
            // 将注册成功后的账号返回到登录页的用户名
            val username = it.data!!.getStringExtra("username")
            if (username.isNullOrEmpty()) {
                binding.username.setText(username!!)
                binding.password.setText("")
            }
        }
    }

    /**
     * 前往注册
     */
    private fun toRegister() {
        startActivityForResultLauncher(Intent(this, RegisterActivity::class.java), registerActivityResultLauncher)
    }
}