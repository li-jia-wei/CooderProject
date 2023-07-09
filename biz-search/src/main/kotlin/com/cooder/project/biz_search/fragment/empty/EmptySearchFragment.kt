package com.cooder.project.biz_search.fragment.empty

import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
import com.cooder.project.common.ui.component.CoAbsListFragment
import com.cooder.project.pub_mod.items.GoodsItem
import com.cooder.project.pub_mod.items.HotTab
import com.cooder.project.pub_mod.model.GoodsMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/4 14:09
 *
 * 介绍：当搜索不到内容时显示
 */
class EmptySearchFragment(
	private val emptySearch: IEmptySearch
) : CoAbsListFragment() {
	
	override fun onLoadMore() {
		emptySearch.doLoadMoreInEmptySearch()
	}
	
	override fun onRefresh() {
		super.onRefresh()
		emptySearch.doRefreshInEmptySearch()
	}
	
	fun bindData(goodsList: List<GoodsMo>?, isInit: Boolean, hasLastSuccessfulSearch: Boolean) {
		if (isNotAlive()) return
		val dataItems = mutableListOf<CoDataItem<*, out CoViewHolder>>()
		if (isInit) {
			dataItems += EmptySearchItem(hasLastSuccessfulSearch)
		}
		goodsList?.forEach {
			dataItems += GoodsItem(it, HotTab())
		}
		finishRefresh(dataItems)
	}
}