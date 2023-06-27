package com.cooder.project.biz_home.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_home.api.AccountApi
import com.cooder.project.biz_home.api.NoticeApi
import com.cooder.project.pub_mod.model.CourseNotice
import com.cooder.project.pub_mod.model.UserProfile

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
	
	/**
	 * 查询通知
	 */
	fun queryCourseNotice(): LiveData<CoResult<CourseNotice>> {
		val liveData = MutableLiveData<CoResult<CourseNotice>>()
		val ignoreInterceptor = listOf(com.cooder.project.common.http.HttpCodeInterceptor::class.java)
		com.cooder.project.common.http.ApiFactory.create(NoticeApi::class.java, ignoreInterceptor).notice().enqueue(object : CoCallback<CourseNotice> {
			override fun onSuccess(response: CoResponse<CourseNotice>) {
				if (response.isSuccessful() && response.data != null) {
					liveData.value = CoResult.success(response.data)
				} else {
					liveData.value = CoResult.failure(response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				liveData.value = CoResult.failure(throwable.message)
			}
		})
		return liveData
	}
	
	/**
	 * 查询个人页面信息
	 */
	fun queryProfile(): LiveData<CoResult<UserProfile>> {
		val liveData = MutableLiveData<CoResult<UserProfile>>()
		com.cooder.project.common.http.ApiFactory.create(AccountApi::class.java).profile().enqueue(object : CoCallback<UserProfile> {
			override fun onSuccess(response: CoResponse<UserProfile>) {
				if (response.isSuccessful() && response.data != null) {
					liveData.value = CoResult.success(response.data)
				} else {
					liveData.value = CoResult.failure(response.message)
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