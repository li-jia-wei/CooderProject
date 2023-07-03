package com.cooder.project.biz_home.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.project.biz_home.api.CategoryApi
import com.cooder.project.biz_home.api.HomeApi
import com.cooder.project.biz_home.model.HomeMo
import com.cooder.project.biz_home.model.TabCategoryMo
import com.cooder.project.common.http.ApiFactory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 16:36
 *
 * 介绍：HomeViewModel
 */
class HomePageViewModel(private val savedState: SavedStateHandle) : ViewModel() {
	
	/**
	 * 查询顶部Tab的类别
	 */
	fun queryTabCategoryList(): LiveData<CoResult<List<TabCategoryMo>>> {
		val liveData = MutableLiveData<CoResult<List<TabCategoryMo>>>()
		savedState.get<CoResult<List<TabCategoryMo>>>("tabCategories")?.let {
			liveData.value = it
			return liveData
		}
		ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategoryMo>> {
			override fun onSuccess(response: CoResponse<List<TabCategoryMo>>) {
				if (response.isSuccessful()) {
					val result = CoResult.success(response.data)
					liveData.value = result
					savedState["tabCategories"] = result
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
	 * 查询当前tab页的数据
	 */
	fun queryTabCategoryList(categoryId: String, pageIndex: Int, pageSize: Int, cacheStrategyType: CacheStrategy.Type): LiveData<CoResult<HomeMo>> {
		val liveData = MutableLiveData<CoResult<HomeMo>>()
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(
			categoryId, pageIndex, pageSize, cacheStrategyType
		).enqueue(object : CoCallback<HomeMo> {
			override fun onSuccess(response: CoResponse<HomeMo>) {
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