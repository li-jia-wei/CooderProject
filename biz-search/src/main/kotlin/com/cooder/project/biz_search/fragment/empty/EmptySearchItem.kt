package com.cooder.project.biz_search.fragment.empty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_search.R
import com.cooder.project.biz_search.databinding.ItemEmptySearchBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/8 19:03
 *
 * 介绍：EmptyItem
 */
class EmptySearchItem(
	private val hasLastSuccessfulSearch: Boolean
) : CoDataItem<Boolean, CoViewBindingHolder<ItemEmptySearchBinding>>() {
	
	override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemEmptySearchBinding> {
		return CoViewBindingHolder(ItemEmptySearchBinding.inflate(inflater, parent, false))
	}
	
	override fun onBindData(holder: CoViewBindingHolder<ItemEmptySearchBinding>, position: Int) {
		if (hasLastSuccessfulSearch) {
			holder.binding.hint.setText(R.string.empty_search_hint_2)
			holder.binding.line.visibility = View.VISIBLE
		} else {
			holder.binding.hint.setText(R.string.empty_search_hint_1)
			holder.binding.line.visibility = View.GONE
		}
	}
	
	override fun getSpanSize(): Int {
		return 1
	}
}