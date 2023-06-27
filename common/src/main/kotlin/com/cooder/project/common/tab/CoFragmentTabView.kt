package com.cooder.project.common.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:05
 *
 * 介绍：将Fragment的操作内聚，提供通用的一些API
 */
class CoFragmentTabView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
	
	/**
	 * 适配器
	 */
	var adapter: CoTabViewAdapter? = null
		set(value) {
			if (field != null || value == null) return
			field = value
		}
	
	/**
	 * 默认全部是以hide隐藏，设置哪些是remove来移除
	 */
	private var removeFragments = mutableSetOf<Class<out Fragment>>()
	
	/**
	 * 增加需要隐藏的Fragment
	 */
	fun addRemoveFragments(fragments: Set<Class<out Fragment>>) {
		removeFragments += fragments
	}
	
	/**
	 * 增加需要隐藏的Fragment
	 */
	fun addRemoveFragments(vararg fragments: Class<out Fragment>) {
		removeFragments += fragments
	}
	
	/**
	 * 增加需要隐藏的Fragment
	 */
	fun addRemoveFragment(fragment: Class<out Fragment>) {
		removeFragments += fragment
	}
	
	private var currentPosition: Int = -1
	
	/**
	 * 设置当前View的Item
	 */
	fun setCurrentItem(@IntRange(from = 0) position: Int) {
		if (adapter == null) {
			throw IllegalStateException("Please call the setAdapter method first!")
		}
		if (adapter == null || position < 0 || position >= adapter!!.getCount()) {
			return
		}
		if (currentPosition != position) {
			currentPosition = position
			adapter!!.instantiateItem(this, position, removeFragments)
		}
	}
	
	/**
	 * 获取当前View的Item
	 */
	fun getCurrentItem(): Int {
		return currentPosition
	}
	
	/**
	 * 获取当前Fragment
	 */
	fun getCurrentFragment(position: Int): Fragment? {
		if (adapter == null) {
			throw IllegalStateException("Please call the setAdapter method first")
		}
		return adapter!!.currentFragment
	}
}