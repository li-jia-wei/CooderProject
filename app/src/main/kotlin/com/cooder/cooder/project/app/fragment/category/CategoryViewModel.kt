package com.cooder.cooder.project.app.fragment.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.restful.CoResult
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.CategoryApi
import com.cooder.cooder.project.app.model.Subcategory
import com.cooder.cooder.project.app.model.TabCategory

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/11 11:38
 *
 * 介绍：CategoryViewModel
 */
class CategoryViewModel : ViewModel() {
	
	private val _categoryListLiveData by lazy { MutableLiveData<CoResult<List<TabCategory>>>() }
	
	val categoryListLiveData: LiveData<CoResult<List<TabCategory>>> = _categoryListLiveData
	
	/**
	 * 查询类别
	 */
	fun queryCategoryList() {
		ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
			override fun onSuccess(response: CoResponse<List<TabCategory>>) {
				val data = response.data
				if (response.isSuccessful() && data != null) {
					_categoryListLiveData.value = CoResult(data)
				} else {
					_categoryListLiveData.value = CoResult(null, false, response.message)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				_categoryListLiveData.value = CoResult(null, false, throwable.message)
			}
		})
	}
	
	private val _subcategoryListLiveData by lazy { MutableLiveData<SubcategoryListMo>() }
	
	val subcategoryListLiveData: LiveData<SubcategoryListMo> = _subcategoryListLiveData
	
	data class SubcategoryListMo(val result: CoResult<List<Subcategory>>, val categoryId: String)
	
	/**
	 * 查询子类别
	 */
	fun querySubcategoryList(categoryId: String) {
		ApiFactory.create(CategoryApi::class.java).querySubcategoryList(categoryId).enqueue(object : CoCallback<List<Subcategory>> {
			override fun onSuccess(response: CoResponse<List<Subcategory>>) {
				if (response.isSuccessful() && response.data != null) {
					_subcategoryListLiveData.value = SubcategoryListMo(CoResult(response.data), categoryId)
				} else {
					_subcategoryListLiveData.value = SubcategoryListMo(CoResult(null, false, response.message), categoryId)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				super.onFailed(throwable)
				_subcategoryListLiveData.value = SubcategoryListMo(CoResult(null, false, throwable.message), categoryId)
			}
		})
	}
}