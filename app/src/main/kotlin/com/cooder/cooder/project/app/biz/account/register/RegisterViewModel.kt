package com.cooder.cooder.project.app.biz.account.register

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
 * 创建：2023/3/10 20:19
 *
 * 介绍：LoginViewModel
 */
class RegisterViewModel : ViewModel() {
	
	private val _registerLiveData by lazy { MutableLiveData<RegisterMo>() }
	
	val registerLiveData: LiveData<RegisterMo> = _registerLiveData
	
	data class RegisterMo(
		val result: CoResult<String>,
		val username: String
	)
	
	/**
	 * 注册
	 */
	fun register(username: String, password: String, moocId: String, courseNotice: String) {
		ApiFactory.create(AccountApi::class.java).register(username, password, moocId, courseNotice)
			.enqueue(object : CoCallback<String> {
				override fun onSuccess(response: CoResponse<String>) {
					if (response.isSuccessful()) {
						_registerLiveData.value = RegisterMo(CoResult(null), username)
					} else {
						_registerLiveData.value = RegisterMo(CoResult(null, false, response.message), username)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					_registerLiveData.value = RegisterMo(CoResult(null, false, throwable.message), username)
				}
			})
	}
}