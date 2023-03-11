package com.cooder.cooder.project.app.biz.account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.AccountApi

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 20:54
 *
 * 介绍：LoginViewModel
 */
class LoginViewModel : ViewModel() {
	
	private val loginLiveData by lazy { MutableLiveData<CoResult<String>>() }
	
	/**
	 * 登录
	 */
	fun login(username: String, password: String): LiveData<CoResult<String>> {
		ApiFactory.create(AccountApi::class.java).login(username, password).enqueue(object : CoCallback<String> {
			override fun onSuccess(response: CoResponse<String>) {
				if (response.isSuccessful() && response.data != null) {
					loginLiveData.value = CoResult(response.data!!)
				} else {
					loginLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				loginLiveData.value = CoResult(null, false, throwable.message)
			}
		})
		return loginLiveData
	}
}