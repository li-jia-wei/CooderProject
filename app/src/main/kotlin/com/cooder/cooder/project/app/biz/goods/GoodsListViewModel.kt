package com.cooder.cooder.project.app.biz.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.log.CoLog
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
	
	private val _goodsListLiveData by lazy { MutableLiveData<CoResult<GoodsList>>() }
	
	val goodsListLiveData: LiveData<CoResult<GoodsList>> = _goodsListLiveData
	
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
	fun queryGoodsList(mo: GoodsListMo) {
		CoLog.i(mo.categoryId)
		ApiFactory.create(GoodsApi::class.java)
			.queryGoodsList(mo.cacheStrategyType, mo.categoryId, mo.subcategoryId, mo.pageSize, mo.pageIndex)
			.enqueue(object : CoCallback<GoodsList> {
				override fun onSuccess(response: CoResponse<GoodsList>) {
					if (response.isSuccessful() && response.data != null) {
						_goodsListLiveData.value = CoResult(response.data)
					} else {
						_goodsListLiveData.value = CoResult(null, false, response.message)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					_goodsListLiveData.value = CoResult(null, false, throwable.message)
				}
			})
	}
}