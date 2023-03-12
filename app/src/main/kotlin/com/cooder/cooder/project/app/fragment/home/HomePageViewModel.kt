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
	
	private val _tabListLiveData by lazy { MutableLiveData<CoResult<List<TabCategory>>>() }
	
	val tabListLiveData: LiveData<CoResult<List<TabCategory>>> = _tabListLiveData
	
	/**
	 * 查询顶部Tab的类别
	 */
	fun queryTabList() {
		ApiFactory.create(HomeApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
			override fun onSuccess(response: CoResponse<List<TabCategory>>) {
				if (response.isSuccessful() && response.data != null) {
					_tabListLiveData.value = CoResult(response.data!!)
				} else {
					_tabListLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				_tabListLiveData.value = CoResult(null, false, throwable.message)
			}
		})
	}
	
	private val _tabCategoryListLiveData by lazy { MutableLiveData<CoResult<HomeModel>>() }
	
	val tabCategoryListLiveData: LiveData<CoResult<HomeModel>> = _tabCategoryListLiveData
	
	data class TabCategoryListMo(
		val cacheStrategyType: CacheStrategy.Type,
		val categoryId: String,
		val pageIndex: Int,
		val pageSize: Int
	)
	
	/**
	 * 查询当前tab页的数据
	 */
	fun queryTabCategoryList(mo: TabCategoryListMo) {
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(
			mo.cacheStrategyType, mo.categoryId, mo.pageIndex, mo.pageSize
		).enqueue(object : CoCallback<HomeModel> {
			override fun onSuccess(response: CoResponse<HomeModel>) {
				if (response.isSuccessful() && response.data != null) {
					_tabCategoryListLiveData.value = CoResult(response.data)
				} else {
					_tabCategoryListLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				_tabCategoryListLiveData.value = CoResult(null, false, throwable.message)
			}
		})
	}
}