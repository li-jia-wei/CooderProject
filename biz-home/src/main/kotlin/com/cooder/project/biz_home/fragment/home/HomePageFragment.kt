package com.cooder.project.biz_home.fragment.home

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cooder.library.ui.tab.common.CoTabLayout
import com.cooder.library.ui.tab.top.CoTabTopInfo
import com.cooder.project.biz_home.R
import com.cooder.project.biz_home.databinding.FragmentHomePageBinding
import com.cooder.project.biz_home.model.TabCategoryMo
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.route.RoutePath
import com.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.project.service_login.LoginServiceProvider

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
		
		binding.searchView.setOnClickListener {
			CoRoute.startActivity(RoutePath.BizSearch.ACTIVITY_SEARCH)
		}
		
		queryTabList()
		
		LoginServiceProvider.loginSuccessObserver(requireContext()) {
			queryTabList()
		}
	}
	
	private fun queryTabList() {
		viewModel.queryTabCategoryList().observe(viewLifecycleOwner) {
			if (it.hasData()) {
				updateUI(it.data!!)
			} else {
				Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
			}
		}
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
	private fun updateUI(data: List<TabCategoryMo>) {
		if (!isAlive() || data.isEmpty()) return
		val defaultColor = requireContext().getColor(R.color.home_tab_top_default)
		val tintColor = requireContext().getColor(R.color.home_tab_top_tint)
		val tabTopInfos = mutableListOf<CoTabTopInfo<Int>>()
		data.forEachIndexed { _, tabCategory ->
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
		
		private val tabs = mutableListOf<TabCategoryMo>()
		
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
		
		fun update(tabs: List<TabCategoryMo>) {
			this.tabs.clear()
			this.tabs += tabs
			notifyDataSetChanged()
		}
	}
}