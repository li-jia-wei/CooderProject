package com.cooder.project.biz_search.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.ui.item.CoAdapter
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.biz_search.R
import com.cooder.project.biz_search.databinding.ItemQuickSearchBinding
import com.cooder.project.biz_search.model.KeyWordMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/3 12:09
 *
 * 介绍：快搜联想词
 */
class QuickSearchView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
	
	private val adapter = CoAdapter(context)
	
	private enum class Status {
		QUICK_SEARCH_NORMAL,
		QUICK_SEARCH_LAST,
		NO_QUICK_SEARCH
	}
	
	init {
		layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		setAdapter(adapter)
		adapter.clearAnimation()
	}
	
	fun bindData(keyWords: List<KeyWordMo>, callback: (KeyWordMo) -> Unit) {
		adapter.removeAllItems()
		if (keyWords.isNotEmpty()) {
			val items = mutableListOf<QuickSearchItem>()
			keyWords.forEachIndexed { index, keyWord: KeyWordMo ->
				items += if (index < keyWords.size - 1) {
					QuickSearchItem(keyWord, callback, Status.QUICK_SEARCH_NORMAL)
				} else {
					QuickSearchItem(keyWord, callback, Status.QUICK_SEARCH_LAST)
				}
			}
			adapter.addItems(items)
		} else {
			adapter.addItem(QuickSearchItem(KeyWordMo("", context.getString(R.string.search_no_quick_search_hint)), null, Status.NO_QUICK_SEARCH))
		}
	}
	
	private inner class QuickSearchItem(
		private val keyWord: KeyWordMo,
		private val callback: ((KeyWordMo) -> Unit)?,
		private val status: Status
	) : CoDataItem<KeyWordMo, CoViewBindingHolder<ItemQuickSearchBinding>>() {
		
		override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): CoViewBindingHolder<ItemQuickSearchBinding> {
			val binding = ItemQuickSearchBinding.inflate(inflater, parent, false)
			return CoViewBindingHolder(binding)
		}
		
		override fun onBindData(holder: CoViewBindingHolder<ItemQuickSearchBinding>, position: Int) {
			val binding = holder.binding
			binding.content.text = keyWord.keyWord
			if (status != Status.NO_QUICK_SEARCH) {
				holder.itemView.setOnClickListener {
					callback?.invoke(keyWord)
				}
			} else {
				holder.itemView.setOnClickListener(null)
			}
			binding.underline.visibility = if (status != Status.QUICK_SEARCH_LAST) View.VISIBLE else View.GONE
			binding.icon.visibility = if (status != Status.NO_QUICK_SEARCH) View.VISIBLE else View.GONE
			binding.content.gravity = if (status != Status.NO_QUICK_SEARCH) Gravity.LEFT or Gravity.CENTER_VERTICAL else Gravity.CENTER
		}
	}
}