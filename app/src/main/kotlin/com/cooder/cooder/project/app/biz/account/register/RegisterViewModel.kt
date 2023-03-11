package com.cooder.cooder.project.app.biz.account.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.AccountApi

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 20:19
 *
 * 介绍：LoginViewModel
 */
class RegisterViewModel : ViewModel() {
	
	companion object {
		const val LOGIN_SUCCESS = "<LOGIN_SUCCESS>"
	}
	
	private val registerLiveData by lazy { MutableLiveData<String>() }
	
	/**
	 * 注册
	 */
	fun register(
		username: String,
		password: String,
		moocId: String,
		courseOrderId: String
	): LiveData<String> {
		ApiFactory.create(AccountApi::class.java).register(username, password, moocId, courseOrderId)
			.enqueue(object : CoCallback<String> {
				override fun onSuccess(response: CoResponse<String>) {
					if (response.isSuccessful()) {
						registerLiveData.value = LOGIN_SUCCESS
					} else {
						registerLiveData.value = response.message
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					registerLiveData.value = throwable.message
				}
			})
		return registerLiveData
	}
}