package com.cooder.project.biz_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.cache.CoStorage
import com.cooder.library.library.executor.CoExecutor
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_search.api.GoodsApi
import com.cooder.project.biz_search.model.QuickSearchListMo
import com.cooder.project.common.http.ApiFactory
import com.cooder.project.pub_mod.model.GoodsListMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/2 11:13
 *
 * 介绍：SearchViewModel
 */
class SearchViewModel : ViewModel() {
	
	private var lastSearchKeyWord: String? = null
	
	private companion object {
		private const val PAGE_SIZE = 10
		private const val TYPE_SEARCH = "search"
		private const val KEY_SEARCH_LAST = "last"
	}
	
	/**
	 * 搜索联想词
	 */
	fun queryQuickSearch(keyWord: String): LiveData<CoResult<QuickSearchListMo>> {
		val liveData = MutableLiveData<CoResult<QuickSearchListMo>>()
		ApiFactory.create(GoodsApi::class.java).querySearchQuick(keyWord).enqueue(object : CoCallback<QuickSearchListMo> {
			override fun onSuccess(response: CoResponse<QuickSearchListMo>) {
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
	 * 搜索商品
	 */
	fun queryGoodsSearch(keyWord: String, pageIndex: Int): LiveData<CoResult<GoodsListMo>> {
		val liveData = MutableLiveData<CoResult<GoodsListMo>>()
		ApiFactory.create(GoodsApi::class.java).querySearchGoods(keyWord, pageIndex, PAGE_SIZE)
			.enqueue(object : CoCallback<GoodsListMo> {
				override fun onSuccess(response: CoResponse<GoodsListMo>) {
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
	 * 保存最近一次搜索到商品的关键字
	 */
	fun saveLastSuccessfulSearchKeyWord(keyWord: String) {
		CoExecutor.execute {
			CoStorage.saveCache(TYPE_SEARCH, KEY_SEARCH_LAST, keyWord)
			this.lastSearchKeyWord = keyWord
		}
	}
	
	/**
	 * 获取最近一次搜索到商品的关键字
	 */
	fun getLastSuccessfulSearchKeyWord(): String? {
		if (this.lastSearchKeyWord == null) {
			this.lastSearchKeyWord = CoStorage.getCache<String>(TYPE_SEARCH, KEY_SEARCH_LAST, null)
		}
		return this.lastSearchKeyWord
	}
}