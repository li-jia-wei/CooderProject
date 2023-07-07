package com.cooder.project.biz_login.register

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
 * 创建：2023/3/10 20:19
 *
 * 介绍：LoginViewModel
 */
class RegisterViewModel : ViewModel() {
	
	data class RegisterMo(
		val success: Boolean,
		val result: CoResult<String>,
		val username: String
	)
	
	/**
	 * 注册
	 */
	fun register(username: String, password: String, moocId: String, courseNotice: String): LiveData<RegisterMo> {
		val liveData = MutableLiveData<RegisterMo>()
		com.cooder.project.common.http.ApiFactory.create(AccountApi::class.java).register(username, password, moocId, courseNotice)
			.enqueue(object : CoCallback<String> {
				override fun onSuccess(response: CoResponse<String>) {
					if (response.isSuccessful(false)) {
						liveData.value = RegisterMo(true, CoResult.success(null, response.message), username)
					} else {
						liveData.value = RegisterMo(false, CoResult.failure(response.message), username)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					liveData.value = RegisterMo(false, CoResult.failure(throwable.message), username)
				}
			})
		return liveData
	}
}