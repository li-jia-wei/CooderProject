package com.cooder.cooder.project.app.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.MainActivity
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.HomeApi
import com.cooder.cooder.project.app.model.HomeModel
import com.cooder.cooder.project.common.ui.component.CoAbsListFragment
import com.cooder.cooder.ui.item.CoDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/7 21:50
 *
 * 介绍：主页TabFragment
 */
class HomePageTabFragment private constructor() : CoAbsListFragment() {
	
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
		categoryId = arguments?.getString("categoryId", DEFAULT_HOT_TAB_CATEGORY_ID)
		
		super.onViewCreated(view, savedInstanceState)
		
		// 查询数据
		queryTabCategoryList()
		
		enableLoadMore {
			queryTabCategoryList()
		}
	}
	
	override fun onRefresh() {
		super.onRefresh()
		
		queryTabCategoryList()
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		val isHotTab = categoryId == DEFAULT_HOT_TAB_CATEGORY_ID
		return if (isHotTab) super.createLayoutManager() else GridLayoutManager(context, 2)
	}
	
	private fun queryTabCategoryList() {
		ApiFactory.create(HomeApi::class.java).queryTabCategoryList(categoryId!!, pageIndex, 10).enqueue(object : CoCallback<HomeModel> {
			override fun onSuccess(response: CoResponse<HomeModel>) {
				if (response.isSuccessful() && response.data != null) {
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
		val dataItems = mutableListOf<CoDataItem<*, *>>()
		data.bannerList?.let {
			dataItems += BannerItem(it)
		}
		data.subcategoryList?.let {
			dataItems += SubcategoryItem(it)
		}
		data.goodsList?.forEach {
			dataItems += GoodsItem(it, categoryId == DEFAULT_HOT_TAB_CATEGORY_ID)
		}
		finishRefresh(dataItems)
		fixTabBottomPadding()
	}
	
	/**
	 * 修复底部Padding
	 */
	private fun fixTabBottomPadding() {
		val bottomView = View(context)
		bottomView.setBackgroundColor(Color.TRANSPARENT)
		val height = (requireActivity() as MainActivity).getTabBottomLayoutHeight()
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, height.dpInt)
		bottomView.layoutParams = params
		adapter.setBottomView(bottomView)
	}
}