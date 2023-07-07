package com.cooder.project.biz_detail.items

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.library.library.util.expends.dp
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_detail.R
import com.cooder.project.biz_detail.databinding.ItemDetailCommentAreaBinding
import com.cooder.project.biz_detail.databinding.ItemDetailCommentBinding
import com.cooder.project.biz_detail.model.CommentMo
import com.cooder.project.biz_detail.model.DetailMo
import com.cooder.project.common.ui.view.expends.loadCircle
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import kotlin.math.min

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/2 16:51
 *
 * 介绍：商品详情 - 评价
 */
class CommentItem(
	private val model: DetailMo
) : CoDataItem<DetailMo, CoViewBindingHolder<ItemDetailCommentBinding>>() {
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemDetailCommentBinding> {
		val binding = ItemDetailCommentBinding.inflate(inflater, parent, false)
		return CoViewBindingHolder(binding)
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemDetailCommentBinding>, position: Int) {
		val binding = holder.binding
		if (model.commentCountTitle.isBlank()) {
			binding.root.visibility = View.GONE
			return
		}
		binding.commentTitle.text = model.commentCountTitle
		model.commentTags?.let { bindCommentTags(binding, holder.context, it) }
		model.commentModels?.let { bindCommentArea(binding, holder.context, it) }
	}
	
	/**
	 * 绑定评论标签
	 */
	private fun bindCommentTags(binding: ItemDetailCommentBinding, context: Context, commentTags: String) {
		binding.root.visibility = View.VISIBLE
		binding.chipGroup.visibility = View.VISIBLE
		commentTags.split(' ').forEachIndexed { index, tag ->
			val chip = if (index < binding.chipGroup.childCount) {
				binding.chipGroup.getChildAt(index) as Chip
			} else {
				generateChip(context).apply {
					binding.chipGroup.addView(this)
				}
			}
			chip.text = tag
		}
	}
	
	/**
	 * 生成Chip
	 */
	private fun generateChip(context: Context): Chip {
		val chip = Chip(context)
		val shape = ShapeAppearanceModel().toBuilder()
			.setAllCornerSizes(6.dp)
			.build()
		chip.shapeAppearanceModel = shape
		chip.chipBackgroundColor = context.getColorStateList(R.color.detail_comment_container_chip_bg)
		chip.setTextColor(context.getColor(R.color.detail_comment_container_chip_text))
		chip.textSize = 13F
		chip.gravity = Gravity.CENTER
		chip.isCheckedIconVisible = false
		chip.isCheckable = false
		chip.isLongClickable = false
		chip.isChipIconVisible = false
		chip.setEnsureMinTouchTargetSize(false)
		return chip
	}
	
	/**
	 * 绑定评论区
	 */
	private fun bindCommentArea(binding: ItemDetailCommentBinding, context: Context, commentModels: List<CommentMo>) {
		binding.root.visibility = View.VISIBLE
		for (index in 0 until min(commentModels.size, 4)) {
			val comment = commentModels[index]
			val areaBinding = if (index < binding.commentArea.childCount) {
				ItemDetailCommentAreaBinding.bind(binding.commentArea.getChildAt(index))
			} else {
				val area = ItemDetailCommentAreaBinding.inflate(LayoutInflater.from(context), binding.commentArea, false)
				binding.commentArea.addView(area.root)
				area
			}
			areaBinding.userAvatar.loadCircle(comment.avatar)
			areaBinding.userNickname.text = comment.nickName
			areaBinding.commentContent.text = comment.content
		}
	}
}