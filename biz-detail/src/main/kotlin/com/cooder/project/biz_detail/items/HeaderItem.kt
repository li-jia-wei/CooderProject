package com.cooder.project.biz_detail.items

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.cooder.library.ui.banner.core.CoBannerMo
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_detail.databinding.ItemDetailHeaderBinding
import com.cooder.project.biz_detail.model.DetailMo
import com.cooder.project.common.ui.view.expends.load
import com.cooder.project.pub_mod.model.SliderImage

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/28 12:23
 *
 * 介绍：商品详情 - 顶部轮播图
 */
class HeaderItem(
	private val sliderImages: List<SliderImage>?,
	private val price: String,
	private val completedNumText: String?,
	private val goodsName: String?
) : CoDataItem<DetailMo, CoViewBindingHolder<ItemDetailHeaderBinding>>() {
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemDetailHeaderBinding>? {
		val binding = ItemDetailHeaderBinding.inflate(inflater, parent, false)
		return CoViewBindingHolder(binding)
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemDetailHeaderBinding>, position: Int) {
		val binding = holder.binding
		val bannerMos = arrayListOf<CoBannerMo>()
		sliderImages?.forEach { sliderImage ->
			sliderImage.url?.let {
				bannerMos += CoBannerMo(it)
			}
		}
		binding.banner.setBannerData(bannerMos)
		binding.banner.setBindAdapter { viewHolder, mo, _ ->
			val imageView = viewHolder.rootView as ImageView
			imageView.load(mo.url)
		}
		binding.price.text = spanPrice(this.price)
		binding.saleDesc.text = completedNumText
		binding.title.text = goodsName
	}
	
	private fun spanPrice(price: String): CharSequence {
		if (price.isEmpty()) return ""
		val ss = SpannableString(price)
		ss.setSpan(AbsoluteSizeSpan(18, true), 1, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		return ss
	}
	
	override fun getSpanSize(): Int {
		return 2
	}
}