package com.cooder.cooder.project.app.main.fragment.home

import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.project.app.main.model.HomeGoods
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:53
 *
 * 介绍：GoodsItem
 */
class GoodsItem(
	goods: HomeGoods
) : CooderDataItem<HomeGoods, RecyclerView.ViewHolder>(goods) {
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
	
	}
}