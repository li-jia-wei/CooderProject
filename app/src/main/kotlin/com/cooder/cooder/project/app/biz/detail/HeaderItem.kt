package com.cooder.cooder.project.app.biz.detail

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.ImageView
import android.widget.TextView
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.model.DetailModel
import com.cooder.cooder.project.app.model.SliderImage
import com.cooder.cooder.project.common.ui.view.expends.load
import com.cooder.cooder.ui.banner.CoBanner
import com.cooder.cooder.ui.banner.core.CoBannerMo
import com.cooder.cooder.ui.banner.indicator.NumberIndicator
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/28 12:23
 *
 * 介绍：HeaderItem
 */
class HeaderItem(
	private val sliderImages: List<SliderImage>?,
	private val price: String,
	private val completedNumText: String?,
	private val goodsName: String?
) : CoDataItem<DetailModel, CoViewHolder>() {
	
	override fun getItemLayoutRes(): Int {
		return R.layout.item_detail_header
	}
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val context = holder.itemView.context
		val bannerMos = arrayListOf<CoBannerMo>()
		sliderImages?.forEach {
			bannerMos += CoBannerMo(it.url)
		}
		val banner: CoBanner = holder.findViewById(R.id.banner)
		val price: TextView = holder.findViewById(R.id.price)
		val saleDesc: TextView = holder.findViewById(R.id.sale_desc)
		val title: TextView = holder.findViewById(R.id.title)
		
		banner.setBannerIndicator(NumberIndicator(context))
		banner.setBannerData(bannerMos)
		banner.setBindAdapter { viewHolder, mo, position ->
			val imageView = viewHolder.rootView as ImageView
			imageView.load(mo.url)
		}
		price.text = spanPrice(this.price)
		saleDesc.text = completedNumText
		title.text = goodsName
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