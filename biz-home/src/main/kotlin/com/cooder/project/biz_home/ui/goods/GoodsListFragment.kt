package com.cooder.project.biz_home.ui.goods

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.ui.component.CoAbsListFragment
import com.cooder.project.pub_mod.items.GoodsItem
import com.cooder.project.pub_mod.model.GoodsListMo

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
	
	private var goodsSize = 0
	
	companion object {
		
		private const val GOODS_ITEM_SPAN = 2
		fun newInstance(categoryId: String, subcategoryId: String): Fragment {
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
		viewModel.queryGoodsList(mo).observe(viewLifecycleOwner) {
			if (it.hasData()) {
				onQueryCategoryGoodsList(it.data!!)
			} else {
				Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
				finishRefresh(null)
			}
		}
	}
	
	private fun onQueryCategoryGoodsList(data: GoodsListMo) {
		val dataItems = mutableListOf<GoodsItem>()
		data.list.forEachIndexed { index, goodsModel ->
			dataItems += GoodsItem(goodsModel, false, GOODS_ITEM_SPAN, goodsSize + index)
		}
		goodsSize += data.list.size
		finishRefresh(dataItems)
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		return GridLayoutManager(context, GOODS_ITEM_SPAN)
	}
}