package com.cooder.cooder.project.app.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.AccountApi
import com.cooder.cooder.project.app.http.api.NoticeApi
import com.cooder.cooder.project.app.http.interceptor.HttpCodeInterceptor
import com.cooder.cooder.project.app.model.CourseNotice
import com.cooder.cooder.project.app.model.UserProfile

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/11 12:27
 *
 * 介绍：ProfileViewModel
 */
class ProfileViewModel : ViewModel() {
	
	private val courseNotice by lazy { MutableLiveData<CoResult<CourseNotice>>() }
	
	/**
	 * 查询通知
	 */
	fun queryCourseNotice(): LiveData<CoResult<CourseNotice>> {
		val ignoreInterceptor = listOf(HttpCodeInterceptor::class.java)
		ApiFactory.create(NoticeApi::class.java, ignoreInterceptor).notice().enqueue(object : CoCallback<CourseNotice> {
			override fun onSuccess(response: CoResponse<CourseNotice>) {
				if (response.isSuccessful() && response.data != null) {
					courseNotice.value = CoResult(response.data)
				} else {
					courseNotice.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				courseNotice.value = CoResult(null, false, throwable.message)
			}
		})
		return courseNotice
	}
	
	private val profileLiveData by lazy { MutableLiveData<CoResult<UserProfile>>() }
	
	/**
	 * 查询个人页面信息
	 */
	fun queryProfile(): LiveData<CoResult<UserProfile>> {
		ApiFactory.create(AccountApi::class.java).profile().enqueue(object : CoCallback<UserProfile> {
			override fun onSuccess(response: CoResponse<UserProfile>) {
				if (response.isSuccessful() && response.data != null) {
					profileLiveData.value = CoResult(response.data)
				} else {
					profileLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				profileLiveData.value = CoResult(null, false, throwable.message)
			}
		})
		return profileLiveData
	}
}