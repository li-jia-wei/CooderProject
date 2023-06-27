package com.cooder.project.biz_login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_login.api.AccountApi

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
	
	/**
	 * 登录
	 */
	fun login(username: String, password: String): LiveData<CoResult<String>> {
		val liveData = MutableLiveData<CoResult<String>>()
		com.cooder.project.common.http.ApiFactory.create(AccountApi::class.java).login(username, password).enqueue(object : CoCallback<String> {
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