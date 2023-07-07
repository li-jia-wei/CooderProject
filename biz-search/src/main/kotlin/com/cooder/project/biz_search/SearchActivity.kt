package com.cooder.project.biz_search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.library.library.util.CoDisplayUtil
import com.cooder.library.library.util.expends.immersiveStatusBar
import com.cooder.project.biz_search.databinding.ActivitySearchBinding
import com.cooder.project.biz_search.fragment.empty.EmptySearchFragment
import com.cooder.project.biz_search.fragment.goods.GoodsSearchFragment
import com.cooder.project.biz_search.fragment.history.HistorySearchFragment
import com.cooder.project.biz_search.fragment.quick.QuickSearchFragment
import com.cooder.project.common.route.RoutePath
import com.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.project.common.ui.component.CoBaseFragment

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
	
	private val quickSearchFragment by lazy { QuickSearchFragment() }
	private val historySearchFragment by lazy { HistorySearchFragment(::doQueryGoodsSearch) }
	private val goodsSearchFragment by lazy { GoodsSearchFragment(::doLoadMoreGoodsSearch) }
	private val emptySearchFragment by lazy { EmptySearchFragment() }
	
	private var keyWord: String? = null
	
	override fun getViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
		return ActivitySearchBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		immersiveStatusBar(true)
		
		initSearchView()
		
		doQueryHistorySearch()
	}
	
	private fun initSearchView() {
		val statusBarsHeight = CoDisplayUtil.getStatusBarsHeight()
		binding.searchView.setTopPadding(statusBarsHeight)
		binding.searchView.setNavListener {
			onBackPressedResultCanceled()
		}
		binding.searchView.setSearchListener {
			doQueryGoodsSearch(it, true)
		}
		binding.searchView.setSearchContentChangeListener {
			if (it.isNotEmpty()) {
				doQueryQuickSearch(it)
			} else {
				doQueryHistorySearch()
			}
		}
		binding.searchView.closeHint()
	}
	
	private fun doQueryQuickSearch(keyWord: String) {
		viewModel.queryQuickSearch(keyWord).observe(this) {
			if (it.hasData()) {
				showFragment(quickSearchFragment)
				quickSearchFragment.bindData(it.data!!.list, keyWord) {
					doQueryGoodsSearch(it, true)
				}
			} else {
				doQueryHistorySearch()
			}
		}
	}
	
	/**
	 * 加载更多商品
	 */
	private fun doLoadMoreGoodsSearch(isInit: Boolean) {
		keyWord?.also {
			doQueryGoodsSearch(it, isInit)
		}
	}
	
	/**
	 * 查询商品
	 */
	private fun doQueryGoodsSearch(keyWord: String, isInit: Boolean) {
		this.keyWord = keyWord
		if (isInit) {
			goodsSearchFragment.pageIndex = 1
			binding.searchView.setKeyWord(keyWord)
		}
		historySearchFragment.doSaveHistorySearch(keyWord)
		binding.searchView.setKeyWordCloseClickable(false)
		viewModel.queryGoodsSearch(keyWord, goodsSearchFragment.pageIndex).observe(this) {
			binding.searchView.setKeyWordCloseClickable(true)
			if ((it.hasData() && it.data!!.list.isNotEmpty()) || !isInit) {
				showFragment(goodsSearchFragment)
				goodsSearchFragment.bindData(it.data!!.list)
			} else {
				showFragment(emptySearchFragment)
			}
		}
	}
	
	private fun doQueryHistorySearch() {
		showFragment(historySearchFragment)
	}
	
	private fun showFragment(fragment: CoBaseFragment<*>) {
		val manager = supportFragmentManager
		val currentFragment = manager.findFragmentById(binding.fragmentContainer.id)
		if (fragment == currentFragment) return
		val beginTransaction = manager.beginTransaction()
		if (currentFragment == null) {
			beginTransaction.add(binding.fragmentContainer.id, fragment)
		} else {
			beginTransaction.replace(binding.fragmentContainer.id, fragment)
		}
		beginTransaction.commitNow()
	}
}