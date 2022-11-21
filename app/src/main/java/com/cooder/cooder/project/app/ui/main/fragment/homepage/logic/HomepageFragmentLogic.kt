package com.cooder.cooder.project.app.ui.main.fragment.homepage.logic

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.ui.main.fragment.homepage.fragment.HomepageHotFragment
import com.cooder.cooder.project.common.tab.CooderFragmentTabView
import com.cooder.cooder.project.common.tab.CooderTabViewAdapter
import com.cooder.cooder.ui.tab.common.CooderTabLayout
import com.cooder.cooder.ui.tab.top.CooderTabTopInfo
import com.cooder.cooder.ui.tab.top.CooderTabTopLayout

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/9 21:41
 *
 * 介绍：HotFragmentLogic
 */
class HomepageFragmentLogic(
	private val fragmentProvider: FragmentProvider,
	savedInstanceState: Bundle?
) {
	
	lateinit var fragmentTabView: CooderFragmentTabView
		private set
	
	lateinit var tabTopLayout: CooderTabTopLayout
		private set
	
	val infoList: MutableList<CooderTabTopInfo<*>> = ArrayList()
	
	var currentItemIndex: Int = 0
		private set
	
	companion object {
		private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
	}
	
	init {
		savedInstanceState?.also {
			currentItemIndex = it.getInt(SAVED_CURRENT_ID)
		}
		initTabTop()
	}
	
	interface FragmentProvider {
		
		fun <T : View> findViewById(@IdRes id: Int): T
		
		fun requireContext(): Context
		
		fun getChildFragmentManager(): FragmentManager
		
		fun getResources(): Resources
	}
	
	fun onSaveInstanceState(outState: Bundle) {
		outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
	}
	
	private fun initTabTop() {
		tabTopLayout = fragmentProvider.findViewById(R.id.tab_top_layout)
		tabTopLayout.setShowInfoCount(1)
		val defaultColor = ContextCompat.getColor(fragmentProvider.requireContext(), R.color.tab_default)
		val tintColor = ContextCompat.getColor(fragmentProvider.requireContext(), R.color.tab_tint)
		val tabNames = fragmentProvider.getResources().getStringArray(R.array.homepage_tab_names)
		tabNames.forEach {
			infoList += CooderTabTopInfo(
				it,
				defaultColor,
				tintColor,
				HomepageHotFragment::class.java
			)
		}
		tabTopLayout.inflateInfo(infoList)
		initFragmentTabView()
		tabTopLayout.addTabSelectedChangeListener(object : CooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CooderTabTopInfo<*>?, nextInfo: CooderTabTopInfo<*>) {
				fragmentTabView.setCurrentItem(index)
				currentItemIndex = index
			}
		})
		tabTopLayout.defaultSelected(infoList[currentItemIndex])
	}
	
	private fun initFragmentTabView() {
		val tabViewAdapter = CooderTabViewAdapter(fragmentProvider.getChildFragmentManager(), infoList)
		fragmentTabView = fragmentProvider.findViewById(R.id.fragment_tab_view)
		fragmentTabView.adapter = tabViewAdapter
	}
}