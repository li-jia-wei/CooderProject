package com.cooder.cooder.project.app.main.fragment.home

import android.os.Bundle
import android.view.View
import com.cooder.cooder.library.restful.CooderCallback
import com.cooder.cooder.library.restful.CooderResponse
import com.cooder.cooder.project.app.main.http.ApiFactory
import com.cooder.cooder.project.app.main.http.api.HomeApi
import com.cooder.cooder.project.app.main.model.HomeModel
import com.cooder.cooder.project.common.ui.component.CooderAbsListFragment
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/7 21:50
 *
 * 介绍：主页TabFragment
 */
class HomePageTabFragment private constructor() : CooderAbsListFragment() {
	
	private var categoryId: String? = null
	
	companion object {
		
		private const val DEFAULT_HOT_TAB_CATEGORY_ID = "1"
		
		/**
		 * 获取实例
		 */
		fun newInstance(categoryId: String): HomePageTabFragment {
			val args = Bundle()
			args.putString("categoryId", categoryId)
			val fragment = HomePageTabFragment()
			fragment.arguments = args
			return fragment
		}
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		categoryId = arguments?.getString("categoryId", DEFAULT_HOT_TAB_CATEGORY_ID)
		queryTabCategoryList()
		
		enableLoadMore {
			queryTabCategoryList()
		}
	}
	
	override fun onRefresh() {
		super.onRefresh()
		queryTabCategoryList()
	}
	
	private fun queryTabCategoryList() {
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(categoryId!!).enqueue(object : CooderCallback<HomeModel> {
			override fun onSuccess(response: CooderResponse<HomeModel>) {
				if (response.isSuccess() && response.data != null) {
					updateUI(response.data!!)
				} else {
					finishRefresh(null)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				finishRefresh(null)
			}
		})
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(data: HomeModel) {
		if (isNotAlive()) return
		val dataItems = mutableListOf<CooderDataItem<*, *>>()
		data.homeBannerList?.let {
			dataItems += BannerItem(it)
		}
		data.subcategoryList?.let {
			dataItems += SubcategoryItem(it)
		}
		data.homeGoodsList?.forEach {
			dataItems += GoodsItem(it)
		}
		finishRefresh(dataItems)
	}
}