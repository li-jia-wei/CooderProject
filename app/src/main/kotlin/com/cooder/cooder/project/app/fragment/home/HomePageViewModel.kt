package com.cooder.cooder.project.app.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.HomeApi
import com.cooder.cooder.project.app.model.HomeModel
import com.cooder.cooder.project.app.model.TabCategory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 16:36
 *
 * 介绍：HomeViewModel
 */
class HomePageViewModel : ViewModel() {
	
	private val tabListLiveData by lazy { MutableLiveData<CoResult<List<TabCategory>>>() }
	
	/**
	 * 查询顶部Tab的类别
	 */
	fun queryTabList(): LiveData<CoResult<List<TabCategory>>> {
		ApiFactory.create(HomeApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
			override fun onSuccess(response: CoResponse<List<TabCategory>>) {
				if (response.isSuccessful() && response.data != null) {
					tabListLiveData.value = CoResult(response.data!!)
				} else {
					tabListLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				tabListLiveData.value = CoResult(null, false, throwable.message)
			}
		})
		return tabListLiveData
	}
	
	private val tabCategoryListLiveData by lazy { MutableLiveData<CoResult<HomeModel>>() }
	
	/**
	 * 查询当前tab页的数据
	 */
	fun queryTabCategoryList(
		cacheStrategyType: CacheStrategy.Type,
		categoryId: String,
		pageIndex: Int,
		pageSize: Int
	): LiveData<CoResult<HomeModel>> {
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(cacheStrategyType, categoryId, pageIndex, pageSize).enqueue(object : CoCallback<HomeModel> {
			override fun onSuccess(response: CoResponse<HomeModel>) {
				if (response.isSuccessful() && response.data != null) {
					tabCategoryListLiveData.value = CoResult(response.data)
				} else {
					tabCategoryListLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				tabCategoryListLiveData.value = CoResult(null, false, throwable.message)
			}
		})
		return tabCategoryListLiveData
	}
}