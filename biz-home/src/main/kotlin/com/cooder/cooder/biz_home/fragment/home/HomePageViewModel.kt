package com.cooder.cooder.biz_home.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cooder.cooder.biz_home.api.CategoryApi
import com.cooder.cooder.biz_home.api.HomeApi
import com.cooder.cooder.common.http.ApiFactory
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.pub_mod.model.HomeModel
import com.cooder.cooder.pub_mod.model.TabCategory

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
	fun queryTabCategoryList(): LiveData<CoResult<List<TabCategory>>> {
		val liveData = MutableLiveData<CoResult<List<TabCategory>>>()
		savedState.get<CoResult<List<TabCategory>>>("tabCategories")?.let {
			liveData.value = it
			return liveData
		}
		ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
			override fun onSuccess(response: CoResponse<List<TabCategory>>) {
				if (response.isSuccessful() && response.data != null) {
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
	fun queryTabCategoryList(categoryId: String, pageIndex: Int, pageSize: Int, cacheStrategyType: CacheStrategy.Type): LiveData<CoResult<HomeModel>> {
		val liveData = MutableLiveData<CoResult<HomeModel>>()
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(
			categoryId, pageIndex, pageSize, cacheStrategyType
		).enqueue(object : CoCallback<HomeModel> {
			override fun onSuccess(response: CoResponse<HomeModel>) {
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