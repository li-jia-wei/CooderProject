package com.cooder.project.biz_detail.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_detail.databinding.ItemDetailGoodsAttrBinding
import com.cooder.project.biz_detail.databinding.ItemDetailGoodsAttrItemBinding
import com.cooder.project.biz_detail.model.DetailMo

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
	private val model: DetailMo
) : CoDataItem<DetailMo, CoViewBindingHolder<ItemDetailGoodsAttrBinding>>() {
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemDetailGoodsAttrBinding> {
		val binding = ItemDetailGoodsAttrBinding.inflate(inflater, parent, false)
		return CoViewBindingHolder(binding)
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemDetailGoodsAttrBinding>, position: Int) {
		val binding = holder.binding
		if (!model.goodAttr.isNullOrEmpty()) {
			binding.attrContainer.visibility = View.VISIBLE
			model.goodAttr.forEachIndexed { index, mutableMap ->
				val attrItemBinding = if (index < binding.attrContainer.childCount) {
					ItemDetailGoodsAttrItemBinding.bind(binding.attrContainer.getChildAt(index))
				} else {
					val itemBinding = ItemDetailGoodsAttrItemBinding.inflate(LayoutInflater.from(holder.context), binding.attrContainer, false)
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