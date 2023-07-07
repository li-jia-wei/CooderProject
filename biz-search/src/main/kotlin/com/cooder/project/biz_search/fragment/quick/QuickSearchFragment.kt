package com.cooder.project.biz_search.fragment.quick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooder.library.ui.item.CoAdapter
import com.cooder.project.biz_search.R
import com.cooder.project.biz_search.databinding.FragmentQuickSearchBinding
import com.cooder.project.biz_search.model.KeyWordMo
import com.cooder.project.common.ui.component.CoBaseFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/4 14:08
 *
 * 介绍：当搜索框输入时显示
 */
class QuickSearchFragment : CoBaseFragment<FragmentQuickSearchBinding>() {
	
	private lateinit var adapter: CoAdapter
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentQuickSearchBinding {
		return FragmentQuickSearchBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		initQuickSearch()
	}
	
	private fun initQuickSearch() {
		binding.quickSearch.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		adapter = CoAdapter(requireContext())
		binding.quickSearch.adapter = adapter
		adapter.clearAnimation()
	}
	
	/**
	 * 设置快搜词条
	 */
	fun bindData(keyWords: List<KeyWordMo>, highlight: String, callback: (String) -> Unit) {
		adapter.removeAllItems()
		if (keyWords.isNotEmpty()) {
			val items = mutableListOf<QuickSearchItem>()
			keyWords.forEachIndexed { index, keyWord: KeyWordMo ->
				items += if (index < keyWords.size - 1) {
					QuickSearchItem(keyWord.keyWord, highlight, callback, QuickSearchType.HAS_DATA_NORMAL)
				} else {
					QuickSearchItem(keyWord.keyWord, highlight, callback, QuickSearchType.HAS_DATA_LAST)
				}
			}
			adapter.addItems(items)
		} else {
			val hint = getString(R.string.quick_search_no_data_hint)
			val hintQuickSearchItem = QuickSearchItem(hint, null, null, QuickSearchType.NO_DATA)
			adapter.addItem(hintQuickSearchItem)
		}
	}
}