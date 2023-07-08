package com.cooder.project.biz_search.fragment.goods

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
 * 介绍：当搜索到内容时显示
 */
class GoodsSearchFragment(
	private val doLoadMore: () -> Unit,
	private val doRefresh: () -> Unit
) : CoAbsListFragment() {
	
	override fun onLoadMore() {
		doLoadMore.invoke()
	}
	
	override fun onRefresh() {
		super.onRefresh()
		doRefresh.invoke()
	}
	
	fun bindData(goodsList: List<GoodsMo>) {
		if (isNotAlive()) return
		val dataItems = mutableListOf<GoodsItem>()
		goodsList.forEach {
			dataItems += GoodsItem(it, HotTab())
		}
		finishRefresh(dataItems)
	}
}