package com.cooder.cooder.project.app.fragment.home

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.biz.account.AccountManager
import com.cooder.cooder.project.app.databinding.FragmentHomePageBinding
import com.cooder.cooder.project.app.model.TabCategory
import com.cooder.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.cooder.ui.tab.common.CoTabLayout
import com.cooder.cooder.ui.tab.top.CoTabTopInfo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:40
 *
 * 介绍：主页 Fragment
 */
class HomePageFragment : CoBaseFragment<FragmentHomePageBinding>() {
	
	private val viewModel by lazy { ViewModelProvider(this)[HomePageViewModel::class.java] }
	
	private var tabTopSelectIndex = DEFAULT_TAB_TOP_SELECT_INDEX
	
	private companion object {
		/**
		 * 默认TabTop选中的索引
		 */
		private const val DEFAULT_TAB_TOP_SELECT_INDEX = 0
	}
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomePageBinding {
		return FragmentHomePageBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		queryTabListObserver()
		queryTabList()
		
		AccountManager.loginSuccessObserver(requireContext()) {
			queryTabList()
		}
	}
	
	private fun queryTabListObserver() {
		viewModel.tabListLiveData.observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				updateUI(it.data!!)
			} else {
				Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
			}
		}
	}
	
	private fun queryTabList() {
		viewModel.queryTabList()
	}
	
	/**
	 * Tab选中改变事件
	 */
	private val onTabSelectedChangeListener = object : CoTabLayout.OnTabSelectedListener<CoTabTopInfo<*>> {
		override fun onTabSelectedChange(index: Int, prevInfo: CoTabTopInfo<*>?, nextInfo: CoTabTopInfo<*>) {
			if (binding.viewPager.currentItem != index) {
				binding.viewPager.setCurrentItem(index, false)
			}
		}
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(data: List<TabCategory>) {
		if (!isAlive() || data.isEmpty()) return
		val defaultColor = ContextCompat.getColor(requireContext(), R.color.home_tab_top_default)
		val tintColor = ContextCompat.getColor(requireContext(), R.color.home_tab_top_tint)
		val tabTopInfos = mutableListOf<CoTabTopInfo<Int>>()
		data.forEachIndexed { index, tabCategory ->
			val tabTopInfo = CoTabTopInfo(tabCategory.categoryName, defaultColor, tintColor)
			tabTopInfos += tabTopInfo
		}
		binding.tabTopLayout.inflateInfo(tabTopInfos)
		binding.tabTopLayout.selectTabInfo(tabTopInfos[DEFAULT_TAB_TOP_SELECT_INDEX])
		binding.tabTopLayout.addTabSelectedChangeListener(onTabSelectedChangeListener)
		val adapter = if (binding.viewPager.adapter == null) {
			val homePagerAdapter = HomePagerAdapter()
			binding.viewPager.adapter = homePagerAdapter
			
			binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
				override fun onPageSelected(position: Int) {
					// 这个方法被触发有两种可能，第一种是切换顶部Tab，第二种是手动滑动翻页
					// 如果是手动滑动翻页
					if (position != tabTopSelectIndex) {
						binding.tabTopLayout.selectTabInfo(tabTopInfos[position])
						tabTopSelectIndex = position
					}
				}
			})
			homePagerAdapter
		} else {
			binding.viewPager.adapter as HomePagerAdapter
		}
		adapter.update(data)
	}
	
	@Suppress("DEPRECATION")
	inner class HomePagerAdapter : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
		
		private val tabs = mutableListOf<TabCategory>()
		
		private val fragments = SparseArray<Fragment>(tabs.size)
		
		override fun getItem(position: Int): Fragment {
			val categoryId = tabs[position].categoryId
			val key = categoryId.toInt()
			var fragment = fragments.get(key, null)
			if (fragment == null) {
				fragment = HomePageTabFragment.newInstance(categoryId)
				fragments.put(key, fragment)
			}
			return fragment
		}
		
		override fun getItemPosition(any: Any): Int {
			// 判断刷新前后的两次Fragment在ViewPager中的位置是否一致
			// 如果改变了返回POSITION_NONE，否则返回POSITION_UNCHANGED
			// 兼顾刷新前后两次Fragment在不同位置和相同位置的情况
			val indexOfValue = fragments.indexOfValue(any as Fragment)
			val fragment = getItem(indexOfValue)
			return if (fragment == any) PagerAdapter.POSITION_UNCHANGED else PagerAdapter.POSITION_NONE
		}
		
		override fun getItemId(position: Int): Long {
			return tabs[position].categoryId.toLong()
		}
		
		override fun getCount(): Int {
			return tabs.size
		}
		
		fun update(tabs: List<TabCategory>) {
			this.tabs.clear()
			this.tabs += tabs
			notifyDataSetChanged()
		}
	}
}