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
import com.cooder.project.common.R
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
	private val goodsSearchFragment by lazy { GoodsSearchFragment(::doLoadMoreFromGoodsSearch, ::doRefreshFromGoodsSearch) }
	private val emptySearchFragment by lazy { EmptySearchFragment(::doLoadMoreFromEmptySearch, ::doRefreshFromEmptySearch) }
	
	private var keyWord: String? = null
	
	override fun getViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
		return ActivitySearchBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		immersiveStatusBar(true)
		
		initSearchView()
		
		toHistorySearchFragment()
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
				toHistorySearchFragment()
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
				toHistorySearchFragment()
			}
		}
	}
	
	/**
	 * 在商品页面加载更多
	 */
	private fun doLoadMoreFromGoodsSearch() {
		keyWord?.also {
			doQueryGoodsSearch(it, false)
		}
	}
	
	/**
	 * 在商品页面刷新
	 */
	private fun doRefreshFromGoodsSearch() {
		keyWord?.also {
			doQueryGoodsSearch(it, true)
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
				viewModel.saveLastSuccessfulSearchKeyWord(keyWord)
				goodsSearchFragment.bindData(it.data!!.list)
			} else {
				doEmptySearchFragment(true)
			}
		}
	}
	
	/**
	 * 前往历史页
	 */
	private fun toHistorySearchFragment(animation: Boolean = false) {
		showFragment(historySearchFragment, animation)
	}
	
	/**
	 * 在空页面加载更多商品
	 */
	private fun doLoadMoreFromEmptySearch() {
		doEmptySearchFragment(false)
	}
	
	/**
	 * 在空商品页面上刷新更多商品
	 */
	private fun doRefreshFromEmptySearch() {
		doEmptySearchFragment(true)
	}
	
	/**
	 * 执行未找到商品页
	 */
	private fun doEmptySearchFragment(isInit: Boolean) {
		if (isInit) {
			emptySearchFragment.pageIndex = 1
		}
		val keyWord = viewModel.getLastSuccessfulSearchKeyWord()
		binding.searchView.setKeyWordCloseClickable(false)
		viewModel.queryGoodsSearch(keyWord ?: "", emptySearchFragment.pageIndex).observe(this) {
			binding.searchView.setKeyWordCloseClickable(true)
			showFragment(emptySearchFragment)
			if (it.hasData()) {
				emptySearchFragment.bindData(it.data!!.list, isInit, keyWord != null)
			} else {
				emptySearchFragment.bindData(null, isInit, keyWord != null)
			}
		}
	}
	
	private fun showFragment(fragment: CoBaseFragment<*>, animation: Boolean = false) {
		val manager = supportFragmentManager
		val currentFragment = manager.findFragmentById(binding.fragmentContainer.id)
		if (fragment == currentFragment) return
		val transaction = manager.beginTransaction()
		if (animation) {
			transaction.setCustomAnimations(R.anim.deceleration_to_right_enter, R.anim.deceleration_to_right_exit)
		}
		if (currentFragment == null) {
			transaction.add(binding.fragmentContainer.id, fragment)
		} else {
			transaction.replace(binding.fragmentContainer.id, fragment)
		}
		transaction.commitNow()
	}
	
	@Suppress("DEPRECATION")
	@Deprecated("Deprecated in Java")
	override fun onBackPressed() {
		val manager = supportFragmentManager
		val currentFragment = manager.findFragmentById(binding.fragmentContainer.id)
		if (currentFragment == historySearchFragment) {
			super.onBackPressed()
		} else {
			toHistorySearchFragment(true)
		}
	}
}