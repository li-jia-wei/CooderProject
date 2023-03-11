package com.cooder.cooder.project.app.biz.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.GoodsApi
import com.cooder.cooder.project.app.model.GoodsList

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 21:40
 *
 * 介绍：GoodsListViewModel
 */
class GoodsListViewModel : ViewModel() {
	
	private val queryGoodsListLiveData by lazy { MutableLiveData<CoResult<GoodsList>>() }
	
	/**
	 * 查询商品列表
	 */
	fun queryGoodsList(
		cacheStrategyType: CacheStrategy.Type,
		categoryId: String,
		subcategoryId: String,
		pageSize: Int,
		pageIndex: Int
	): LiveData<CoResult<GoodsList>> {
		ApiFactory.create(GoodsApi::class.java)
			.queryGoodsList(cacheStrategyType, categoryId, subcategoryId, pageSize, pageIndex)
			.enqueue(object : CoCallback<GoodsList> {
				override fun onSuccess(response: CoResponse<GoodsList>) {
					if (response.isSuccessful() && response.data != null) {
						queryGoodsListLiveData.value = CoResult(response.data)
					} else {
						queryGoodsListLiveData.value = CoResult(null, false, response.message)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					queryGoodsListLiveData.value = CoResult(null, false, throwable.message)
				}
			})
		return queryGoodsListLiveData
	}
}