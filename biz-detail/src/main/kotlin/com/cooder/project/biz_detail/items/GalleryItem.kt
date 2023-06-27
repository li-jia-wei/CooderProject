package com.cooder.project.biz_detail.items

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
import com.cooder.project.common.ui.view.expends.load
import com.cooder.project.pub_mod.model.SliderImage

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/4 22:09
 *
 * 介绍：商品详情 - 商品相册
 */
class GalleryItem(
	private val slideImage: SliderImage
) : CoDataItem<SliderImage, CoViewHolder>() {
	
	private lateinit var imageView: ImageView
	
	private var parentWidth: Int = 0
	
	override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
		imageView = ImageView(parent.context)
		imageView.scaleType = ImageView.ScaleType.CENTER_CROP
		imageView.setBackgroundColor(Color.WHITE)
		return imageView
	}
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val imageView: ImageView = holder.itemView as ImageView
		if (!slideImage.url.isNullOrEmpty()) {
			imageView.load(slideImage.url!!) {
				val width = it.intrinsicWidth
				val height = it.intrinsicHeight
				val params = imageView.layoutParams ?: RecyclerView.LayoutParams(
					parentWidth,
					RecyclerView.LayoutParams.MATCH_PARENT
				)
				params.width = parentWidth
				params.height = (height / (width.toFloat() / parentWidth)).toInt()
				imageView.layoutParams = params
				false
			}
		}
	}
	
	override fun onViewAttachedToWindow(holder: CoViewHolder) {
		parentWidth = (holder.itemView.parent as ViewGroup).measuredWidth
		val params = holder.itemView.layoutParams
		if (params.width != parentWidth) {
			params.width = parentWidth
			params.height = parentWidth
			holder.itemView.layoutParams = params
		}
	}
}