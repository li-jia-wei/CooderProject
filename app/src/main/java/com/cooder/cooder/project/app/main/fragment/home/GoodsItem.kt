package com.cooder.cooder.project.app.main.fragment.home

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.model.HomeGoods
import com.cooder.cooder.project.common.ui.view.expends.loadCorner
import com.cooder.cooder.ui.item.CoDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:53
 *
 * 介绍：产品Item
 */
class GoodsItem(
	goods: HomeGoods,
	private val hotTab: Boolean
) : CoDataItem<HomeGoods, RecyclerView.ViewHolder>(goods) {
	
	companion object {
		private const val IMAGE_CORNER = 10
	}
	
	override fun getItemLayoutRes(): Int {
		return if (hotTab) R.layout.layout_home_goods_list_item1 else R.layout.layout_home_goods_list_item2
	}
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
		val context = holder.itemView.context
		val image: ImageView = holder.itemView.findViewById(R.id.item_image)
		val title: TextView = holder.itemView.findViewById(R.id.item_title)
		val labelContainer: LinearLayout = holder.itemView.findViewById(R.id.item_label_container)
		val price: TextView = holder.itemView.findViewById(R.id.item_price)
		val saleDesc: TextView = holder.itemView.findViewById(R.id.item_sale_desc)
		image.loadCorner(data.sliderImage, IMAGE_CORNER)
		title.text = data.goodsName
		price.text = getRealPrice(data.marketPrice)
		saleDesc.text = data.completedNumText
		
		val tags = data.tags
		if (tags.isNotEmpty()) {
			labelContainer.visibility = View.VISIBLE
			val split = tags.split(" ")
			for ((index, tag) in split.withIndex()) {
				val labelView: TextView = if (index > labelContainer.childCount - 1) {
					val view = createLabelView(context, index != 0)
					labelContainer.addView(view)
					view
				} else {
					labelContainer.getChildAt(index) as TextView
				}
				labelView.text = tag
			}
		} else {
			labelContainer.visibility = View.GONE
		}
		if (!hotTab) {
			val margin = 2.dpInt
			val param = holder.itemView.layoutParams as RecyclerView.LayoutParams
			val parentLeft = coAdapter?.getAttachRecyclerView()?.left ?: 0
			val parentPaddingLeft = coAdapter?.getAttachRecyclerView()?.paddingLeft ?: 0
			val itemLeft = holder.itemView.left
			if (itemLeft == parentLeft + parentPaddingLeft) {   // 处于列表左边
				param.rightMargin = margin
			} else {
				param.leftMargin = margin
			}
			holder.itemView.layoutParams = param
		}
	}
	
	private fun getRealPrice(price: String): String {
		var realPrice = price
		if (realPrice == "") {
			realPrice = "¥未知"
		}
		if (!realPrice.startsWith("¥")) {
			realPrice = "¥$realPrice"
		}
		return realPrice
	}
	
	private fun createLabelView(context: Context, hasStartMargin: Boolean): TextView {
		val labelView = TextView(context)
		labelView.setTextColor(ContextCompat.getColor(context, R.color.home_goods_label_font))
		labelView.textSize = 9F
		labelView.gravity = Gravity.CENTER
		labelView.setBackgroundResource(R.drawable.shape_goods_label)
		val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 14.dpInt)
		if (hasStartMargin) {
			params.marginStart = 5.dpInt
		}
		labelView.layoutParams = params
		return labelView
	}
	
	override fun getSpanSize(): Int {
		return 1
	}
}