package com.cooder.project.biz_detail.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
import com.cooder.project.biz_detail.databinding.ItemDetailSimilarTitleBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/20 18:34
 *
 * 介绍：商品详情 - 相似商品
 */
class SimilarTitleItem : CoDataItem<Nothing, CoViewHolder>() {
	
	override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
		return ItemDetailSimilarTitleBinding.inflate(inflater, parent, false).root
	}
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
	
	}
}