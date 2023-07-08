package com.cooder.project.biz_search.fragment.quick

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_search.databinding.ItemQuickSearchBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/4 16:23
 *
 * 介绍：SearchQuickItem
 */
class QuickSearchItem(
	private val keyWord: String,
	private val highlight: String?,
	private val callback: ((String) -> Unit)?,
	private val type: QuickSearchType
) : CoDataItem<Any, CoViewBindingHolder<ItemQuickSearchBinding>>() {
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemQuickSearchBinding> {
		return CoViewBindingHolder(ItemQuickSearchBinding.inflate(inflater, parent, false))
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemQuickSearchBinding>, position: Int) {
		val binding = holder.binding
		binding.content.text = keyWord
		if (type != QuickSearchType.NO_DATA) {
			holder.itemView.setOnClickListener {
				callback?.invoke(keyWord)
			}
		} else {
			holder.itemView.setOnClickListener(null)
		}
		binding.underline.visibility = if (type == QuickSearchType.HAS_DATA_NORMAL) View.VISIBLE else View.GONE
		binding.icon.visibility = if (type != QuickSearchType.NO_DATA) View.VISIBLE else View.GONE
		binding.content.gravity = if (type != QuickSearchType.NO_DATA) Gravity.START or Gravity.CENTER_VERTICAL else Gravity.CENTER
		if (type != QuickSearchType.NO_DATA) {
			setSpannableString(binding.content, highlight!!)
		}
	}
	
	private fun setSpannableString(textView: TextView, highlight: String) {
		val content = textView.text.toString()
		val highlightChars = highlight.replace(" ", "").toSet().joinToString("")
		val highlightIndexSet = mutableSetOf<Int>()
		content.forEachIndexed { i, c ->
			if (highlightChars.contains(c, true)) {
				highlightIndexSet += i
			}
		}
		val spannableString = SpannableString(content)
		highlightIndexSet.forEach {
			val colorSpan = ForegroundColorSpan(Color.RED)
			spannableString.setSpan(colorSpan, it, it + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		}
		textView.text = spannableString
	}
}