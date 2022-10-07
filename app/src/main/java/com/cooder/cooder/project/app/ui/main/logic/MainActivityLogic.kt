@file:Suppress("MemberVisibilityCanBePrivate")

package com.cooder.cooder.project.app.ui.main.logic

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.ui.main.fragment.*
import com.cooder.cooder.project.common.tab.CooderFragmentTabView
import com.cooder.cooder.project.common.tab.CooderTabViewAdapter
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomInfo
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomLayout
import com.cooder.cooder.ui.tab.common.ICooderTabLayout

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/4 15:47
 *
 * 文件介绍：MainActivity逻辑层
 */
class MainActivityLogic(
	private val activityProvider: ActivityProvider,
	savedInstanceState: Bundle?
) {
	lateinit var fragmentTabView: CooderFragmentTabView private set
	
	lateinit var tabBottomLayout: CooderTabBottomLayout private set
	
	val infoList: MutableList<CooderTabBottomInfo<*>> = ArrayList()
	
	/**
	 * 当前页面索引
	 */
	var currentItemIndex: Int = 0
		private set
	
	companion object {
		private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
	}
	
	init {
		// fix: 不保留活动导致的Fragment重叠问题
		savedInstanceState?.also {
			currentItemIndex = it.getInt(SAVED_CURRENT_ID)
		}
		initTabBottom()
	}
	
	interface ActivityProvider {
		fun <T : View> findViewById(@IdRes id: Int): T
		
		fun getContext(): Context
		
		fun getSupportFragmentManager(): FragmentManager
	}
	
	/**
	 * 让Activity中的onSaveInstanceState调用
	 */
	fun onSaveInstanceState(outState: Bundle) {
		outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
	}
	
	/**
	 * 初始化底部导航栏
	 */
	private fun initTabBottom() {
		tabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout)
		tabBottomLayout.setEnableSliding(true)
		tabBottomLayout.setTabAlpha(0.9F)
		val iconFont = "fonts/alibaba_iconfont.ttf"
		
		// 首页
		val homePageInfo = CooderTabBottomInfo<Int>(
			R.string.home_page_title,
			iconFont,
			R.string.home_page_icon_home,
			R.string.home_page_icon_home_fill,
			R.color.tab_bottom_default,
			R.color.tab_bottom_tint,
			HomePageFragment::class.java
		)
		infoList += homePageInfo
		
		// 收藏
		val favoriteInfo = CooderTabBottomInfo<Int>(
			R.string.favorite_title,
			iconFont,
			R.string.favorite_icon_collection,
			R.string.favotite_icon_collection_fill,
			R.color.tab_bottom_default,
			R.color.tab_bottom_tint,
			FavoriteFragment::class.java
		)
		infoList += favoriteInfo
		
		// 分类
		val categoryInfo = CooderTabBottomInfo<Int>(
			R.string.category_title,
			iconFont,
			R.string.category_icon_category,
			R.string.category_icon_category,
			R.color.tab_bottom_default,
			R.color.tab_bottom_tint,
			CategoryFragment::class.java
		)
		infoList += categoryInfo
		
		// 推荐
		val recommendInfo = CooderTabBottomInfo<Int>(
			R.string.recommend_title,
			iconFont,
			R.string.recommend_icon_good,
			R.string.recommend_icon_good_fill,
			R.color.tab_bottom_default,
			R.color.tab_bottom_tint,
			RecommendFragment::class.java
		)
		infoList += recommendInfo
		
		// 我的
		val profileInfo = CooderTabBottomInfo<Int>(
			R.string.profile_title,
			iconFont,
			R.string.profile_icon_account,
			R.string.profile_icon_account_fill,
			R.color.tab_bottom_default,
			R.color.tab_bottom_tint,
			ProfileFragment::class.java
		)
		infoList += profileInfo
		tabBottomLayout.inflateInfo(infoList)
		initFragmentTabView()
		tabBottomLayout.addTabSelectedChangeListener(object : ICooderTabLayout.OnTabSelectedListener<CooderTabBottomInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CooderTabBottomInfo<*>?, nextInfo: CooderTabBottomInfo<*>) {
				fragmentTabView.setCurrentItem(index)
				currentItemIndex = index
			}
		})
		tabBottomLayout.defaultSelected(infoList[currentItemIndex])
	}
	
	/**
	 * 初始化Fragment
	 */
	private fun initFragmentTabView() {
		val tabViewAdapter = CooderTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList)
		fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view)
		fragmentTabView.adapter = tabViewAdapter
	}
}