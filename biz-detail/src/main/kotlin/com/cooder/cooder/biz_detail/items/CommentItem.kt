package com.cooder.cooder.biz_detail.items

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cooder.cooder.biz_detail.R
import com.cooder.cooder.biz_detail.databinding.ItemDetailCommentAreaBinding
import com.cooder.cooder.biz_detail.databinding.ItemDetailCommentBinding
import com.cooder.cooder.common.ui.view.expends.loadCircle
import com.cooder.cooder.library.util.expends.dp
import com.cooder.cooder.pub_mod.model.CommentModel
import com.cooder.cooder.pub_mod.model.DetailModel
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder
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
    private val model: DetailModel
) : CoDataItem<DetailModel, CoViewHolder>() {

    private lateinit var binding: ItemDetailCommentBinding

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
        binding = ItemDetailCommentBinding.inflate(inflater, parent, false)
        return binding.root
    }

    override fun onBindData(holder: CoViewHolder, position: Int) {
        if (model.commentCountTitle.isBlank()) {
            binding.root.visibility = View.GONE
            return
        }
        binding.commentTitle.text = model.commentCountTitle
        model.commentTags?.let { bindCommentTags(holder.context, it) }
        model.commentModels?.let { bindCommentArea(holder.context, it) }
    }

    /**
     * 绑定评论标签
     */
    private fun bindCommentTags(context: Context, commentTags: String) {
        binding.root.visibility = View.VISIBLE
        binding.chipGroup.visibility = View.VISIBLE
        commentTags.split(' ').forEachIndexed { index, tag ->
            val chipLabel = if (index < binding.chipGroup.childCount) {
                binding.chipGroup.getChildAt(index) as Chip
            } else {
                val chipLabel = Chip(context)
                val shape = ShapeAppearanceModel().toBuilder()
                    .setAllCornerSizes(4.dp)
                    .build()
                chipLabel.shapeAppearanceModel = shape
                chipLabel.chipBackgroundColor = ContextCompat.getColorStateList(
                    context,
                    R.color.detail_comment_container_label_bg
                )
                chipLabel.setTextColor(ContextCompat.getColor(context, R.color.gray))
                chipLabel.textSize = 14F
                chipLabel.gravity = Gravity.CENTER
                chipLabel.isCheckedIconVisible = false
                chipLabel.isCheckable = false
                chipLabel.isChipIconVisible = false
                chipLabel.setEnsureMinTouchTargetSize(false)
                binding.chipGroup.addView(chipLabel)
                chipLabel
            }
            chipLabel.text = tag
        }
    }

    /**
     * 绑定评论区
     */
    private fun bindCommentArea(context: Context, commentModels: List<CommentModel>) {
        binding.root.visibility = View.VISIBLE
        for (index in 0 until min(commentModels.size, 4)) {
            val comment = commentModels[index]
            val areaBinding = if (index < binding.commentArea.childCount) {
                ItemDetailCommentAreaBinding.bind(binding.commentArea.getChildAt(index))
            } else {
                val area = ItemDetailCommentAreaBinding.inflate(
                    LayoutInflater.from(context),
                    binding.commentArea,
                    false
                )
                binding.commentArea.addView(area.root)
                area
            }
            areaBinding.userAvatar.loadCircle(comment.avatar)
            areaBinding.userNickname.text = comment.nickName
            areaBinding.commentContent.text = comment.content
        }
    }
}