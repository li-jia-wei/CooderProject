package com.cooder.project.biz_detail.items

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.library.ui.item.CoAdapter
import com.cooder.library.ui.item.CoDataBindingHolder
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_detail.R
import com.cooder.project.biz_detail.databinding.ItemDetailShopBinding
import com.cooder.project.biz_detail.databinding.ItemDetailShopGoodsBinding
import com.cooder.project.biz_detail.model.DetailMo
import com.cooder.project.common.ui.view.expends.load
import com.cooder.project.pub_mod.items.GoodsItem
import com.cooder.project.pub_mod.items.Tab
import com.cooder.project.pub_mod.model.GoodsMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/3 18:52
 *
 * 介绍：商品详情 - 店铺
 */
class ShopItem(
	private val model: DetailMo
) : CoDataItem<DetailMo, CoViewBindingHolder<ItemDetailShopBinding>>() {
	
	private companion object {
		private const val SHOP_GOODS_ITEM_SPAN = 3
	}
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemDetailShopBinding> {
		val binding = ItemDetailShopBinding.inflate(inflater, parent, false)
		return CoViewBindingHolder(binding)
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemDetailShopBinding>, position: Int) {
		val binding = holder.binding
		val context = holder.context
		// 商品logo
		val shop = model.shop
		binding.shopLogo.load(shop.logo)
		binding.shopTitle.text = shop.name
		binding.shopDesc.text = context.getString(R.string.detail_shop_desc, shop.goodsNum, shop.completedNum)
		
		// 商品标签
		val tags = shop.evaluation.split(' ')
		if (tags.size > 1) {
			binding.tagContainer.visibility = View.VISIBLE
			for (i in 0 until tags.size / 2) {
				val tagView = if (i < binding.tagContainer.childCount) {
					binding.tagContainer.getChildAt(i) as TextView
				} else {
					val tag = TextView(context)
					val params =
						LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
					params.weight = 1F
					tag.layoutParams = params
					tag.gravity = Gravity.CENTER
					tag.textSize = 14F
					tag.setTextColor(context.getColor(R.color.gray))
					val name = tags[i * 2]
					val level = tags[i * 2 + 1]
					tag.text = spanTag(context, name, level)
					binding.tagContainer.addView(tag)
					tag
				}
				val name = tags[i * 2]
				val level = tags[i * 2 + 1]
				tagView.text = spanTag(context, name, level)
			}
		}
		
		// 商品栏
		model.flowGoods?.let { goods ->
			binding.shopGoods.visibility = View.VISIBLE
			if (binding.shopGoods.adapter == null) {
				binding.shopGoods.layoutManager = GridLayoutManager(context, SHOP_GOODS_ITEM_SPAN)
				binding.shopGoods.adapter = CoAdapter(context)
			}
			val dataItem = mutableListOf<ShopGoodsItem>()
			goods.forEachIndexed { index, goodsModel ->
				dataItem += ShopGoodsItem(goodsModel, index)
			}
			val adapter = binding.shopGoods.adapter as CoAdapter
			adapter.removeAllItems()
			adapter.addItems(dataItem, true)
		}
	}
	
	private class ShopGoodsItem(model: GoodsMo, index: Int) : GoodsItem(model, Tab(SHOP_GOODS_ITEM_SPAN, index)) {
		
		override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoDataBindingHolder<ViewDataBinding> {
			val binding = ItemDetailShopGoodsBinding.inflate(inflater, parent, false)
			return CoDataBindingHolder(binding)
		}
		
		override fun onViewAttachedToWindow(holder: CoDataBindingHolder<ViewDataBinding>) {
			super.onViewAttachedToWindow(holder)
			val image = (holder.binding as ItemDetailShopGoodsBinding).itemImage
			val parent = holder.parent
			val availableWidth = parent.measuredWidth - parent.paddingStart - parent.paddingEnd
			val imageWidth = availableWidth / SHOP_GOODS_ITEM_SPAN
			val params = image.layoutParams
			params.width = imageWidth
			params.height = imageWidth
			image.layoutParams = params
		}
	}
	
	/**
	 * 设置tag的富文本
	 */
	private fun spanTag(context: Context, name: String, level: String): CharSequence {
		val ssLevel = SpannableString(level)
		ssLevel.setSpan(
			ForegroundColorSpan(context.getColor(R.color.detail_shop_tag_level_fg)),
			0,
			ssLevel.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		ssLevel.setSpan(
			BackgroundColorSpan(context.getColor(R.color.detail_shop_tag_level_bg)),
			0,
			ssLevel.length,
			Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		val ssbTag = SpannableStringBuilder()
		ssbTag.append(name)
		ssbTag.append(ssLevel)
		return ssbTag
	}
}