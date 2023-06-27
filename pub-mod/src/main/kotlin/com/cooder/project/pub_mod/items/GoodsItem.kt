package com.cooder.project.pub_mod.items

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewDataBindingHolder
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.route.RoutePath
import com.cooder.project.pub_mod.BR
import com.cooder.project.pub_mod.R
import com.cooder.project.pub_mod.databinding.ItemHomeGoodsList1Binding
import com.cooder.project.pub_mod.databinding.ItemHomeGoodsList2Binding
import com.cooder.project.pub_mod.model.GoodsModel

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:53
 *
 * 介绍：商品Item
 */
open class GoodsItem(
	private val goodsModel: GoodsModel,
	private val hotTab: Boolean,
	private val span: Int,
	private val index: Int
) : CoDataItem<GoodsModel, CoViewDataBindingHolder<ViewDataBinding>>() {
	
	private lateinit var binding: ViewDataBinding
	
	companion object {
		const val IMAGE_CORNER = 10
		private val MARGIN = 3.dpInt
	}
	
	override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewDataBindingHolder<ViewDataBinding> {
		binding = if (hotTab) {
			ItemHomeGoodsList1Binding.inflate(inflater, parent, false)
		} else {
			ItemHomeGoodsList2Binding.inflate(inflater, parent, false)
		}
		return CoViewDataBindingHolder(binding)
	}
	
	override fun onBindData(holder: CoViewDataBindingHolder<ViewDataBinding>, position: Int) {
		val context = holder.context
		
		holder.binding.setVariable(BR.goodsModel, goodsModel)
		
		val labelContainer: LinearLayout? = holder.findViewById(R.id.item_label_container)
		if (labelContainer != null) {
			val tags = goodsModel.tags
			if (!tags.isNullOrBlank()) {
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
		}
		if (!hotTab) {
			val param = holder.itemView.layoutParams as RecyclerView.LayoutParams
			if (index % span == 0) {
				param.rightMargin = MARGIN
				param.leftMargin = MARGIN * 2
			} else if (index % span == span - 1) {
				param.leftMargin = MARGIN
				param.rightMargin = MARGIN * 2
			} else {
				param.leftMargin = MARGIN
				param.rightMargin = MARGIN
			}
			if (index / span == 0) {
				param.topMargin = MARGIN * 2
			} else {
				param.topMargin = MARGIN
			}
			param.bottomMargin = MARGIN
			holder.itemView.layoutParams = param
		}
		holder.itemView.setOnClickListener {
			val bundle = Bundle()
			bundle.putString("goodsId", goodsModel.goodsId)
			bundle.putSerializable("goodsModel", goodsModel)
			CoRoute.startActivity(RoutePath.BizDetail.ACTIVITY_DETAIL, bundle)
		}
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