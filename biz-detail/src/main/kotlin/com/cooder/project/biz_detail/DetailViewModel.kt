package com.cooder.project.biz_detail

import androidx.lifecycle.*
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_detail.api.FavoriteApi
import com.cooder.project.biz_detail.api.GoodsApi
import com.cooder.project.biz_detail.model.DetailMo
import com.cooder.project.biz_detail.model.FavoriteMo
import com.cooder.project.common.http.ApiFactory

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
		class DetailViewModelFactory(private val goodsId: String) :
			ViewModelProvider.NewInstanceFactory() {
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
			return ViewModelProvider(
				viewModelStoreOwner,
				DetailViewModelFactory(goodsId)
			)[DetailViewModel::class.java]
		}
	}
	
	fun queryDetail(): LiveData<CoResult<DetailMo>> {
		val liveData = MutableLiveData<CoResult<DetailMo>>()
		if (goodsId != null) {
			ApiFactory.create(GoodsApi::class.java).queryDetail(goodsId)
				.enqueue(object : CoCallback<DetailMo> {
					override fun onSuccess(response: CoResponse<DetailMo>) {
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
		}
		return liveData
	}
	
	fun toggleFavorite(): LiveData<CoResult<Boolean>> {
		val favoriteLiveData = MutableLiveData<CoResult<Boolean>>()
		if (goodsId != null) {
			ApiFactory.create(FavoriteApi::class.java).favorite(goodsId)
				.enqueue(object : CoCallback<FavoriteMo> {
					override fun onSuccess(response: CoResponse<FavoriteMo>) {
						if (response.isSuccessful()) {
							favoriteLiveData.postValue(CoResult.success(response.data!!.isFavorite))
						} else {
							favoriteLiveData.value = CoResult.failure(response.message)
						}
					}
					
					override fun onFailed(throwable: Throwable) {
						super.onFailed(throwable)
						favoriteLiveData.value = CoResult.failure(throwable.message)
					}
				})
		}
		return favoriteLiveData
	}
}