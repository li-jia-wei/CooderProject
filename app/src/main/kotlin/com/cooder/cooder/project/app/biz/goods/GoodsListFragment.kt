package com.cooder.cooder.project.app.biz.goods

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.project.app.fragment.home.GoodsItem
import com.cooder.cooder.project.app.model.GoodsList
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.common.ui.component.CoAbsListFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 12:27
 *
 * 介绍：GoodsListFragment
 */
class GoodsListFragment : CoAbsListFragment() {
	
	private val viewModel by lazy {
		ViewModelProvider(this)[GoodsListViewModel::class.java]
	}
	
	@JvmField
	@Autowired
	var categoryId = ""
	
	@JvmField
	@Autowired
	var subcategoryId = ""
	
	companion object {
		fun newInstance(categoryId: String, subcategoryId: String): Fragment? {
			val args = Bundle()
			args.putString("categoryId", categoryId)
			args.putString("subcategoryId", subcategoryId)
			val fragment = GoodsListFragment()
			fragment.arguments = args
			return fragment
		}
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		CoRoute.inject(this)
		
		queryGoodsListObserver()
		queryGoodsList(CacheStrategy.Type.CACHE_ONLY)
	}
	
	override fun onRefresh() {
		super.onRefresh()
		queryGoodsList(CacheStrategy.Type.NET_CACHE)
	}
	
	override fun onLoadMore() {
		super.onLoadMore()
		queryGoodsList(CacheStrategy.Type.NET_CACHE)
	}
	
	private fun queryGoodsList(cacheStrategyType: CacheStrategy.Type) {
		val mo = GoodsListViewModel.GoodsListMo(cacheStrategyType, categoryId, subcategoryId, 10, pageIndex)
		viewModel.queryGoodsList(mo)
	}
	
	/**
	 * 查询商品列表
	 */
	private fun queryGoodsListObserver() {
		viewModel.goodsListLiveData.observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				onQueryCategoryGoodsList(it.data!!)
			} else {
				Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
				finishRefresh(null)
			}
		}
	}
	
	private fun onQueryCategoryGoodsList(data: GoodsList) {
		val dataItems = mutableListOf<GoodsItem>()
		data.list.forEach {
			dataItems += GoodsItem(it, false)
		}
		finishRefresh(dataItems)
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		return GridLayoutManager(context, 2)
	}
}