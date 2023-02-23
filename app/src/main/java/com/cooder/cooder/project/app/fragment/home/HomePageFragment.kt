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
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.FragmentHomePageBinding
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.CategoryApi
import com.cooder.cooder.project.app.model.TabCategory
import com.cooder.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.cooder.ui.tab.top.CoTabTopInfo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:40
 *
 * 介绍：主页Fragment
 */
class HomePageFragment : CoBaseFragment<FragmentHomePageBinding>() {
	
	private var tabTopSelectIndex = DEFAULT_TAB_TOP_SELECT_INDEX
	
	private var isSuccessful = false
	
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
		
		queryTabList()
	}
	
	/**
	 * 查询顶部Tab
	 */
	private fun queryTabList() {
		if (!isSuccessful) {
			ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
				override fun onSuccess(response: CoResponse<List<TabCategory>>) {
					val data: List<TabCategory>? = response.data
					if (response.isSuccessful() && data != null) {
						isSuccessful = true
						updateUI(data)
					} else {
						Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
				}
			})
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
		binding.tabTopLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
			if (binding.viewPager.currentItem != index) {
				binding.viewPager.setCurrentItem(index, false)
			}
		}
		binding.viewPager.adapter = HomePagerAdapter(data)
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
	}
	
	inner class HomePagerAdapter(
		private val tabs: List<TabCategory>
	) : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
		
		private val fragments = SparseArray<Fragment>(tabs.size)
		
		override fun getItem(position: Int): Fragment {
			var fragment = fragments[position, null]
			if (fragment == null) {
				fragment = HomePageTabFragment.newInstance(tabs[position].categoryId)
				fragments.put(position, fragment)
			}
			return fragment
		}
		
		override fun getCount(): Int {
			return tabs.size
		}
	}
}