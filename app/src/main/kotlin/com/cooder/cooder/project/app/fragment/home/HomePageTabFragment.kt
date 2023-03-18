package com.cooder.cooder.project.app.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.MainActivity
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
	
	private val viewModel by lazy { ViewModelProvider(this)[HomePageViewModel::class.java] }
	
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
		
		viewModel.tabCategoryListLiveData.observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				updateUI(it.data!!)
			} else {
				showToast(it.msg)
				finishRefresh(null)
			}
		}
		
		// 查询数据
		queryTabCategoryList(CacheStrategy.Type.CACHE_ONLY_NET_CACHE)
		
		enableLoadMore {
			queryTabCategoryList(CacheStrategy.Type.NET_CACHE)
		}
	}
	
	override fun onRefresh() {
		super.onRefresh()
		queryTabCategoryList(CacheStrategy.Type.NET_CACHE)
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		val isHotTab = categoryId == DEFAULT_HOT_TAB_CATEGORY_ID
		return if (isHotTab) super.createLayoutManager() else GridLayoutManager(context, 2)
	}
	
	private fun queryTabCategoryList(cacheStrategy: CacheStrategy.Type) {
		val mo = HomePageViewModel.TabCategoryListMo(cacheStrategy, categoryId!!, pageIndex, 10)
		viewModel.queryTabCategoryList(mo)
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