package com.cooder.project.biz_search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.library.library.util.CoDisplayUtil
import com.cooder.library.library.util.expends.immersiveStatusBar
import com.cooder.project.biz_search.R
import com.cooder.project.biz_search.databinding.ActivitySearchBinding
import com.cooder.project.biz_search.ui.SearchActivity.Status.*
import com.cooder.project.biz_search.view.QuickSearchView
import com.cooder.project.common.route.RoutePath
import com.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.project.common.ui.view.EmptyView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/02 07:30
 *
 * 介绍：搜索页
 */
@Route(path = RoutePath.BizSearch.ACTIVITY_SEARCH)
class SearchActivity : CoBaseActivity<ActivitySearchBinding>() {
	
	private val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }
	
	private var status = INIT
	
	enum class Status {
		INIT,
		EMPTY,
		HISTORY,
		QUICK_SEARCH,
		GOODS_SEARCH
	}
	
	private lateinit var emptyView: EmptyView
	private lateinit var quickSearchView: QuickSearchView
	
	override fun getViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
		return ActivitySearchBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		immersiveStatusBar(true)
		
		initSearchView()
		
		updateStatus(EMPTY)
	}
	
	private fun initSearchView() {
		val statusBarsHeight = CoDisplayUtil.getStatusBarsHeight()
		binding.searchView.setTopPadding(statusBarsHeight)
		binding.searchView.setNavListener {
			onBackPressedResultCanceled()
		}
		binding.searchView.setStatusChangeListener { lastStatus, currentStatus ->
		
		}
		binding.searchView.setSearchListener {
			viewModel.querySearchGoods(it, 1, 10).observe(this) {
			
			}
		}
		binding.searchView.setSearchContentChangeListener {
			if (it.isBlank()) {
				updateStatus(EMPTY)
			} else {
				viewModel.queryQuickSearch(it).observe(this) {
					if (it.hasData()) {
						updateStatus(QUICK_SEARCH)
						quickSearchView.bindData(it.data!!.list) {
							Toast.makeText(this, it.keyWord, Toast.LENGTH_SHORT).show()
						}
					}
				}
			}
		}
		binding.searchView.closeHint()
	}
	
	private fun onEmpty() {
		if (!::emptyView.isInitialized) {
			emptyView = EmptyView(this)
			emptyView.apply {
				this.setDesc(R.string.search_empty_desc)
				this.setIcon(R.string.search_empty_icon)
				this.goneRefreshButton()
				addViewToManager(this)
			}
		}
		displayView(emptyView)
	}
	
	private fun onHistory() {
	
	}
	
	private fun onQuickSearch() {
		if (!::quickSearchView.isInitialized) {
			quickSearchView = QuickSearchView(this)
			addViewToManager(quickSearchView)
		}
		displayView(quickSearchView)
	}
	
	private fun onGoodsSearch() {
	
	}
	
	private fun updateStatus(newStatus: Status) {
		if (status != newStatus) {
			when (newStatus) {
				EMPTY -> onEmpty()
				HISTORY -> onHistory()
				QUICK_SEARCH -> onQuickSearch()
				GOODS_SEARCH -> onGoodsSearch()
				INIT -> {}
			}
			status = newStatus
		}
	}
	
	/**
	 * 添加到View的管理器中
	 */
	private fun addViewToManager(view: View) {
		if (binding.viewManager.indexOfChild(view) == -1) {
			val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
			binding.viewManager.addView(view, params)
		}
	}
	
	/**
	 * 展示哪一个View
	 */
	private fun displayView(view: View) {
		val index = binding.viewManager.indexOfChild(view)
		if (index != -1) {
			binding.viewManager.displayedChild = index
		}
	}
}