package com.cooder.project.biz_home.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_home.api.AccountApi
import com.cooder.project.biz_home.api.NoticeApi
import com.cooder.project.biz_home.model.CourseNoticeMo
import com.cooder.project.biz_home.model.UserProfileMo
import com.cooder.project.common.http.ApiFactory

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
	fun queryCourseNotice(): LiveData<CoResult<CourseNoticeMo>> {
		val liveData = MutableLiveData<CoResult<CourseNoticeMo>>()
		val ignoreInterceptor = listOf(com.cooder.project.common.http.HttpCodeInterceptor::class.java)
		ApiFactory.create(NoticeApi::class.java, ignoreInterceptor).notice().enqueue(object : CoCallback<CourseNoticeMo> {
			override fun onSuccess(response: CoResponse<CourseNoticeMo>) {
				if (response.isSuccessful()) {
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
	fun queryProfile(): LiveData<CoResult<UserProfileMo>> {
		val liveData = MutableLiveData<CoResult<UserProfileMo>>()
		ApiFactory.create(AccountApi::class.java).profile().enqueue(object : CoCallback<UserProfileMo> {
			override fun onSuccess(response: CoResponse<UserProfileMo>) {
				if (response.isSuccessful()) {
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