package com.cooder.project.biz_search.fragment.history

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cooder.library.library.util.CoDisplayUtil
import com.cooder.library.library.util.expends.dp
import com.cooder.project.biz_search.R
import com.cooder.project.biz_search.databinding.FragmentHistorySearchBinding
import com.cooder.project.common.dialog.HintDialog
import com.cooder.project.common.ui.component.CoBaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/4 14:10
 *
 * 介绍：当搜索框为空时显示
 */
class HistorySearchFragment(
	private val queryGoodsSearch: (keyWord: String, isInit: Boolean) -> Unit
) : CoBaseFragment<FragmentHistorySearchBinding>() {
	
	private val viewModel by lazy { ViewModelProvider(this)[HistorySearchViewModel::class.java] }
	private val histories = mutableListOf<String>()
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHistorySearchBinding {
		return FragmentHistorySearchBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		binding.historyClear.setOnClickListener {
			doClearHistorySearch()
		}
		doQueryHistorySearch()
	}
	
	fun doSaveHistorySearch(history: String) {
		viewModel.saveSearchHistory(history)
	}
	
	private fun doQueryHistorySearch() {
		viewModel.queryHistorySearch().observe(viewLifecycleOwner) {
			bindData(it.data!!)
		}
	}
	
	private fun bindData(histories: List<String>) {
		if (histories.isEmpty()) {
			binding.historyTitle.visibility = View.GONE
			binding.historyGroup.visibility = View.GONE
			return
		}
		binding.historyTitle.visibility = View.VISIBLE
		binding.historyGroup.visibility = View.VISIBLE
		this.histories.clear()
		this.histories += histories
		histories.forEachIndexed { index, history ->
			val chip = if (index < binding.historyGroup.childCount) {
				binding.historyGroup.getChildAt(index) as Chip
			} else {
				generateChip(requireContext()).apply {
					binding.historyGroup.addView(this)
				}
			}
			chip.text = history
			chip.setOnClickListener {
				queryGoodsSearch.invoke(histories[index], true)
			}
		}
	}
	
	private fun doClearHistorySearch() {
		HintDialog.Builder(requireContext())
			.setTitle(R.string.history_search_clear_title)
			.setConfirm(R.string.history_search_clear, R.color.history_search_clear) {
				viewModel.clearHistorySearch()
				binding.historyGroup.removeAllViews()
				binding.historyTitle.visibility = View.GONE
				binding.historyGroup.visibility = View.GONE
			}
			.show()
	}
	
	/**
	 * 生成Chip
	 */
	private fun generateChip(context: Context): Chip {
		val chip = Chip(context)
		chip.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
			.setAllCornerSizes(20.dp)
			.build()
		chip.chipBackgroundColor = context.getColorStateList(R.color.history_search_chip_bg)
		chip.setTextColor(context.getColor(R.color.history_search_chip_text))
		chip.textSize = 13F
		chip.gravity = Gravity.CENTER
		chip.isCheckedIconVisible = false
		chip.isChipIconVisible = false
		chip.ellipsize = TextUtils.TruncateAt.END
		chip.isSingleLine = true
		chip.maxWidth = CoDisplayUtil.getDisplayWidth(CoDisplayUtil.Unit.PX) / 4
		chip.setEnsureMinTouchTargetSize(false)
		return chip
	}
}