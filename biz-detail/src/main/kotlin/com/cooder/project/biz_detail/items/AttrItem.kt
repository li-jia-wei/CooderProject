package com.cooder.project.biz_detail.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
import com.cooder.project.biz_detail.databinding.ItemDetailGoodsAttrBinding
import com.cooder.project.biz_detail.databinding.ItemDetailGoodsAttrItemBinding
import com.cooder.project.pub_mod.model.DetailModel

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/4 16:59
 *
 * 介绍：商品详情 - 商品属性
 */
class AttrItem(
	private val model: DetailModel
) : CoDataItem<DetailModel, CoViewHolder>() {
	
	private lateinit var binding: ItemDetailGoodsAttrBinding
	
	override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
		binding = ItemDetailGoodsAttrBinding.inflate(inflater, parent, false)
		return binding.root
	}
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val context = holder.context
		if (!model.goodAttr.isNullOrEmpty()) {
			binding.attrContainer.visibility = View.VISIBLE
			model.goodAttr?.forEachIndexed { index, mutableMap ->
				val attrItemBinding = if (index < binding.attrContainer.childCount) {
					ItemDetailGoodsAttrItemBinding.bind(binding.attrContainer.getChildAt(index))
				} else {
					val itemBinding = ItemDetailGoodsAttrItemBinding.inflate(
						LayoutInflater.from(context),
						binding.attrContainer,
						false
					)
					binding.attrContainer.addView(itemBinding.root)
					itemBinding
				}
				val entity = mutableMap.entries.first()
				attrItemBinding.itemName.text = entity.key
				attrItemBinding.itemValue.text = entity.value
			}
		}
		model.goodDescription?.let {
			binding.attrDesc.visibility = View.VISIBLE
			binding.attrDesc.text = it
		}
	}
}