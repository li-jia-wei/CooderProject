package com.cooder.cooder.biz_login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.biz_login.api.AccountApi
import com.cooder.cooder.common.http.ApiFactory
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult

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
	
	data class LoginMo(
		val username: String,
		val password: String
	)
	
	/**
	 * 登录
	 */
	fun login(mo: LoginMo): LiveData<CoResult<String>> {
		val liveData = MutableLiveData<CoResult<String>>()
		ApiFactory.create(AccountApi::class.java).login(mo.username, mo.password).enqueue(object : CoCallback<String> {
			override fun onSuccess(response: CoResponse<String>) {
				if (response.isSuccessful() && response.data != null) {
					liveData.value = CoResult.success(response.data)
				} else {
					liveData.value = CoResult.success(response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				liveData.value = CoResult.failure(throwable.message)
			}
		})
		return liveData
	}
}