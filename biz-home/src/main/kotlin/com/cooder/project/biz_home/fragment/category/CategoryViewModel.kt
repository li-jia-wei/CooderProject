package com.cooder.project.biz_home.fragment.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cooder.library.library.restful.CoCallback
import com.cooder.library.library.restful.CoResponse
import com.cooder.library.library.restful.CoResult
import com.cooder.project.biz_home.api.CategoryApi
import com.cooder.project.biz_home.model.SubcategoryMo
import com.cooder.project.biz_home.model.TabCategoryMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/11 11:38
 *
 * 介绍：CategoryViewModel
 */
class CategoryViewModel(
	private val savedState: SavedStateHandle
) : ViewModel() {
	
	/**
	 * 查询类别
	 */
	fun queryTabCategoryList(): LiveData<CoResult<List<TabCategoryMo>>> {
		val liveData = MutableLiveData<CoResult<List<TabCategoryMo>>>()
		savedState.get<CoResult<List<TabCategoryMo>>>("tabCategories")?.let {
			liveData.value = it
			return liveData
		}
		com.cooder.project.common.http.ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategoryMo>> {
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
	
	data class SubcategoryListMo(val result: CoResult<List<SubcategoryMo>>, val categoryId: String)
	
	/**
	 * 查询子类别
	 */
	fun querySubcategoryList(categoryId: String): LiveData<SubcategoryListMo> {
		val liveData = MutableLiveData<SubcategoryListMo>()
		com.cooder.project.common.http.ApiFactory.create(CategoryApi::class.java).querySubcategoryList(categoryId).enqueue(object : CoCallback<List<SubcategoryMo>> {
			override fun onSuccess(response: CoResponse<List<SubcategoryMo>>) {
				if (response.isSuccessful()) {
					liveData.value = SubcategoryListMo(CoResult.success(response.data), categoryId)
				} else {
					liveData.value = SubcategoryListMo(CoResult.failure(response.message), categoryId)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				liveData.value = SubcategoryListMo(CoResult.failure(throwable.message), categoryId)
			}
		})
		return liveData
	}
}