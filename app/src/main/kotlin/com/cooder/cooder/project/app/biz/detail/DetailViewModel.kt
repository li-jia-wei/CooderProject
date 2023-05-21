package com.cooder.cooder.project.app.biz.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.FavoriteApi
import com.cooder.cooder.project.app.http.api.GoodsApi
import com.cooder.cooder.project.app.model.DetailModel
import com.cooder.cooder.project.app.model.Favorite

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/27 20:41
 *
 * 介绍：商品详情 ViewModel
 */
class DetailViewModel(
	private val goodsId: String?
) : ViewModel() {
	
	companion object {
		class DetailViewModelFactory(private val goodsId: String) : ViewModelProvider.NewInstanceFactory() {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				try {
					val constructor = modelClass.getConstructor(String::class.java)
					return constructor.newInstance(goodsId)
				} catch (e: Exception) {
					// Ignore
				}
				return super.create(modelClass)
			}
		}
		
		fun get(goodsId: String, viewModelStoreOwner: ViewModelStoreOwner): DetailViewModel {
			return ViewModelProvider(viewModelStoreOwner, DetailViewModelFactory(goodsId))[DetailViewModel::class.java]
		}
	}
	
	private val _detailLiveData = MutableLiveData<CoResult<DetailModel>>()
	
	val detailLiveData: LiveData<CoResult<DetailModel>> = _detailLiveData
	
	fun queryDetail() {
		if (goodsId != null) {
			ApiFactory.create(GoodsApi::class.java).queryDetail(goodsId).enqueue(object : CoCallback<DetailModel> {
				override fun onSuccess(response: CoResponse<DetailModel>) {
					if (response.isSuccessful() && response.data != null) {
						_detailLiveData.value = CoResult(response.data)
					} else {
						_detailLiveData.value = CoResult(null, response.message)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					_detailLiveData.value = CoResult(null, throwable.message)
				}
			})
		}
	}
	
	fun toggleFavorite(): LiveData<CoResult<Boolean>> {
		val favoriteLiveData = MutableLiveData<CoResult<Boolean>>()
		if (goodsId != null) {
			ApiFactory.create(FavoriteApi::class.java).favorite(goodsId).enqueue(object : CoCallback<Favorite> {
				override fun onSuccess(response: CoResponse<Favorite>) {
					if (response.isSuccessful() && response.data != null) {
						favoriteLiveData.postValue(CoResult(response.data!!.isFavorite))
					} else {
						favoriteLiveData.value = CoResult(null, response.message)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					super.onFailed(throwable)
					favoriteLiveData.value = CoResult(null, throwable.message)
				}
			})
		}
		return favoriteLiveData
	}
}