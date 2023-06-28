package com.cooder.project.biz_home.ui.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.project.biz_home.api.GoodsApi
import com.cooder.project.pub_mod.model.GoodsList

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
	
	data class GoodsListMo(
		val cacheStrategyType: CacheStrategy.Type,
		val categoryId: String,
		val subcategoryId: String,
		val pageSize: Int,
		val pageIndex: Int
	)
	
	/**
	 * 查询商品列表
	 */
	fun queryGoodsList(mo: GoodsListMo): LiveData<CoResult<GoodsList>> {
		val liveData = MutableLiveData<CoResult<GoodsList>>()
		com.cooder.project.common.http.ApiFactory.create(GoodsApi::class.java)
			.queryGoodsList(mo.cacheStrategyType, mo.categoryId, mo.subcategoryId, mo.pageSize, mo.pageIndex)
			.enqueue(object : CoCallback<GoodsList> {
				override fun onSuccess(response: CoResponse<GoodsList>) {
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