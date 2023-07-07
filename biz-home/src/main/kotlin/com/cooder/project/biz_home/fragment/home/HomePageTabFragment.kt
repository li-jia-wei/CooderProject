package com.cooder.project.biz_home.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.item.CoDataItem
import com.cooder.project.biz_home.MainActivity
import com.cooder.project.biz_home.model.HomeMo
import com.cooder.project.common.ui.component.CoAbsListFragment
import com.cooder.project.pub_mod.items.GoodsItem
import com.cooder.project.pub_mod.items.HotTab
import com.cooder.project.pub_mod.items.Tab

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/7 21:50
 *
 * 介绍：主页TabFragment
 */
class HomePageTabFragment : CoAbsListFragment() {
	
	private val viewModel by lazy { ViewModelProvider(this)[HomePageViewModel::class.java] }
	
	private var categoryId: String? = null
	
	companion object {
		
		private const val DEFAULT_HOT_TAB_CATEGORY_ID = "1"
		private const val GOODS_ITEM_SPAN = 2
		
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
		doQueryTabCategoryList(CacheStrategy.Type.CACHE_ONLY_NET_CACHE)
	}
	
	override fun onLoadMore() {
		doQueryTabCategoryList(CacheStrategy.Type.NET_ONLY)
	}
	
	override fun onRefresh() {
		super.onRefresh()
		doQueryTabCategoryList(CacheStrategy.Type.NET_ONLY)
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		val isHotTab = categoryId == DEFAULT_HOT_TAB_CATEGORY_ID
		return if (isHotTab) super.createLayoutManager() else GridLayoutManager(context, 2)
	}
	
	private fun doQueryTabCategoryList(cacheStrategyType: CacheStrategy.Type) {
		viewModel.queryTabCategoryList(categoryId!!, pageIndex, 10, cacheStrategyType).observe(viewLifecycleOwner) {
			if (it.hasData()) {
				updateUI(it.data!!)
			} else {
				showToast(it.msg)
				finishRefresh(null)
			}
		}
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(data: HomeMo) {
		if (isNotAlive()) return
		val dataItems = mutableListOf<CoDataItem<*, *>>()
		data.bannerList?.let {
			dataItems += BannerItem(it)
		}
		data.subcategoryList?.let {
			dataItems += SubcategoryItem(it)
		}
		data.goodsList?.forEachIndexed { index, goodsModel ->
			val tab = if (categoryId == DEFAULT_HOT_TAB_CATEGORY_ID) HotTab() else Tab(GOODS_ITEM_SPAN, index)
			dataItems += GoodsItem(goodsModel, tab)
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