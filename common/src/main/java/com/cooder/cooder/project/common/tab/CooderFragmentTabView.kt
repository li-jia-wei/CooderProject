package com.cooder.cooder.project.common.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 23:05
 *
 * 文件介绍：将Fragment的操作内聚，提供通用的一些API
 */
class CooderFragmentTabView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
	
	/**
	 * 适配器
	 */
	var adapter: CooderTabViewAdapter? = null
		set(value) {
			if (field != null || value == null) return
			field = value
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
			adapter!!.instantiateItem(this, position)
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