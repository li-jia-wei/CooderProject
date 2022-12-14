package com.cooder.cooder.project.app.main.fragment.home

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.restful.CooderCallback
import com.cooder.cooder.library.restful.CooderResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.http.ApiFactory
import com.cooder.cooder.project.app.main.http.api.CategoryApi
import com.cooder.cooder.project.app.main.model.TabCategory
import com.cooder.cooder.project.common.ui.component.CooderBaseFragment
import com.cooder.cooder.ui.tab.top.CooderTabTopInfo
import com.cooder.cooder.ui.tab.top.CooderTabTopLayout

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 23:40
 *
 * 文件介绍：主页Fragment
 */
class HomePageFragment : CooderBaseFragment() {
	
	private lateinit var tabTopLayout: CooderTabTopLayout
	private lateinit var viewPager: ViewPager
	
	private var tabTopSelectIndex = DEFAULT_TAB_TOP_SELECT_INDEX
	
	private companion object {
		/**
		 * 默认TabTop选中的索引
		 */
		private const val DEFAULT_TAB_TOP_SELECT_INDEX = 0
	}
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_home_page
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		tabTopLayout = view.findViewById(R.id.tab_top_layout)
		viewPager = view.findViewById(R.id.view_pager)
		
		queryTabList()
	}
	
	/**
	 * 查询顶部Tab
	 */
	private fun queryTabList() {
		ApiFactory.create(CategoryApi::class.java).queryTab().enqueue(object : CooderCallback<List<TabCategory>> {
			override fun onSuccess(response: CooderResponse<List<TabCategory>>) {
				val data: List<TabCategory>? = response.data
				if (response.isSuccess() && data != null) {
					updateUI(data)
					CooderLog.i(data)
				} else {
					showToast(response.msg)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				showToast(throwable.message)
			}
		})
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(data: List<TabCategory>) {
		if (!isAlive() || data.isEmpty()) return
		val defaultColor = ContextCompat.getColor(requireContext(), R.color.home_tab_top_default)
		val tintColor = ContextCompat.getColor(requireContext(), R.color.home_tab_top_tint)
		val tabTopInfos = mutableListOf<CooderTabTopInfo<Int>>()
		data.forEachIndexed { index, tabCategory ->
			val tabTopInfo = CooderTabTopInfo(tabCategory.categoryName, defaultColor, tintColor)
			tabTopInfos += tabTopInfo
		}
		tabTopLayout.inflateInfo(tabTopInfos)
		tabTopLayout.selectTabInfo(tabTopInfos[DEFAULT_TAB_TOP_SELECT_INDEX])
		tabTopLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
			if (viewPager.currentItem != index) {
				viewPager.setCurrentItem(index, false)
			}
		}
		viewPager.adapter = HomePagerAdapter(data)
		viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
			override fun onPageSelected(position: Int) {
				// 这个方法被触发有两种可能，第一种是切换顶部Tab，第二种是手动滑动翻页
				// 如果是手动滑动翻页
				if (position != tabTopSelectIndex) {
					tabTopLayout.selectTabInfo(tabTopInfos[position])
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
			return fragments.size()
		}
	}
}