@file:Suppress("MemberVisibilityCanBePrivate")

package com.cooder.cooder.project.app.logic

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.ActivityMainBinding
import com.cooder.cooder.project.app.fragment.*
import com.cooder.cooder.project.app.fragment.ProfileFragment
import com.cooder.cooder.project.app.fragment.category.CategoryFragment
import com.cooder.cooder.project.app.fragment.home.HomePageFragment
import com.cooder.cooder.project.common.tab.CoTabViewAdapter
import com.cooder.cooder.ui.tab.bottom.CoTabBottomInfo
import com.cooder.cooder.ui.tab.common.CoTabLayout

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 15:47
 *
 * 介绍：MainActivity逻辑层
 */
class MainActivityLogic(
	private val binding: ActivityMainBinding,
	private val activityProvider: ActivityProvider,
	savedInstanceState: Bundle?
) {
	
	private val infoList: MutableList<CoTabBottomInfo<*>> = ArrayList()
	
	/**
	 * 当前页面索引
	 */
	private var currentItemIndex: Int = 0
		private set
	
	companion object {
		private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
	}
	
	init {
		// fix: 不保留活动导致的Fragment重叠问题
		savedInstanceState?.let {
			currentItemIndex = it.getInt(SAVED_CURRENT_ID)
		}
		initTabBottom()
	}
	
	interface ActivityProvider {
		
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
		binding.tabBottomLayout.setTabAlpha(0.9F)
		binding.tabBottomLayout.setEnableSliding(false)
		val iconFont = "font/iconfont_default.ttf"
		
		// 首页
		val homePageInfo = CoTabBottomInfo<Int>(
			R.string.home_title,
			iconFont,
			R.string.home_icon_home,
			R.string.home_icon_home_fill,
			R.color.tab_default,
			R.color.tab_tint,
			HomePageFragment::class.java
		)
		infoList += homePageInfo
		
		// 收藏
		val favoriteInfo = CoTabBottomInfo<Int>(
			R.string.favorite_title,
			iconFont,
			R.string.favorite_icon_collection,
			R.string.favorite_icon_collection_fill,
			R.color.tab_default,
			R.color.tab_tint,
			FavoriteFragment::class.java
		)
		infoList += favoriteInfo
		
		// 分类
		val categoryInfo = CoTabBottomInfo<Int>(
			R.string.category_title,
			iconFont,
			R.string.category_icon_category,
			R.string.category_icon_category,
			R.color.tab_default,
			R.color.tab_tint,
			CategoryFragment::class.java
		)
		infoList += categoryInfo
		
		// 推荐
		val recommendInfo = CoTabBottomInfo<Int>(
			R.string.recommend_title,
			iconFont,
			R.string.recommend_icon_good,
			R.string.recommend_icon_good_fill,
			R.color.tab_default,
			R.color.tab_tint,
			RecommendFragment::class.java
		)
		infoList += recommendInfo
		
		// 我的
		val profileInfo = CoTabBottomInfo<Int>(
			R.string.profile_title,
			iconFont,
			R.string.profile_icon_account,
			R.string.profile_icon_account_fill,
			R.color.tab_default,
			R.color.tab_tint,
			ProfileFragment::class.java
		)
		infoList += profileInfo
		binding.tabBottomLayout.inflateInfo(infoList)
		// 初始化FragmentTabView
		val tabViewAdapter = CoTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList)
		binding.fragmentTabView.adapter = tabViewAdapter
		
		binding.tabBottomLayout.addTabSelectedChangeListener(object : CoTabLayout.OnTabSelectedListener<CoTabBottomInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CoTabBottomInfo<*>?, nextInfo: CoTabBottomInfo<*>) {
				binding.fragmentTabView.setCurrentItem(index)
				currentItemIndex = index
			}
		})
		binding.tabBottomLayout.selectTabInfo(infoList[currentItemIndex])
	}
}