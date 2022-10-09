package com.cooder.cooder.project.common.tab

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cooder.cooder.project.common.R
import com.cooder.cooder.project.common.tab.ToggleSpeedType.*
import com.cooder.cooder.ui.tab.common.ICooderTabInfo

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:36
 *
 * 文件介绍：CooderTabViewAdapter
 */
class CooderTabViewAdapter(
	private val fragmentManager: FragmentManager,
	private val infoList: MutableList<out ICooderTabInfo<*>>,
	private val speedType: ToggleSpeedType = DECELERATION
) {
	
	var currentFragment: Fragment? = null
		private set
	
	private var lastPosition: Int = -1
	private var currentPosition: Int = -1
	
	/**
	 * 实例化以及显示指定位置的Fragment
	 * @param remove 是否使用remove，false - hide
	 */
	@JvmOverloads
	fun instantiateItem(container: View, position: Int, removeFragments: Set<Class<out Fragment>>? = null) {
		lastPosition = currentPosition
		currentPosition = position
		val transaction = fragmentManager.beginTransaction()
		if (lastPosition != -1 && currentPosition != -1) {
			transaction.setToggleAnimation(currentPosition - lastPosition)
		}
		currentFragment?.also {
			if (removeFragments != null && removeFragments.contains(it::class.java)) {
				transaction.remove(it)
			} else {
				transaction.hide(it)
			}
		}
		val tag = "${container.id}:$position"
		currentFragment = fragmentManager.findFragmentByTag(tag)?.also {
			transaction.show(it)
		} ?: getItem(position)?.also {
			if (!it.isAdded) transaction.add(container.id, it, tag)
		}
		transaction.commitAllowingStateLoss()
	}
	
	/**
	 * 通过position获取Fragment
	 */
	fun getItem(position: Int): Fragment? = try {
		infoList[position].fragment?.newInstance()
	} catch (e: Exception) {
		null
	}
	
	/**
	 * 获取到导航栏的数量
	 */
	fun getCount(): Int {
		return infoList.size
	}
	
	/**
	 * 设置切换页面动画
	 * @param direction 大于0:左或上 等于0:不变 小于0:右或下
	 */
	private fun FragmentTransaction.setToggleAnimation(direction: Int) {
		if (direction > 0) {
			when (speedType) {
				AVERAGE -> setCustomAnimations(R.anim.average_to_left_enter, R.anim.average_to_left_exit)
				ACCELERATION -> setCustomAnimations(R.anim.acceleration_to_left_enter, R.anim.acceleration_to_left_exit)
				DECELERATION -> setCustomAnimations(R.anim.deceleration_to_left_enter, R.anim.deceleration_to_left_exit)
			}
		} else if (direction < 0) {
			when (speedType) {
				AVERAGE -> setCustomAnimations(R.anim.average_to_right_enter, R.anim.average_to_right_exit)
				ACCELERATION -> setCustomAnimations(R.anim.acceleration_to_right_enter, R.anim.acceleration_to_right_exit)
				DECELERATION -> setCustomAnimations(R.anim.deceleration_to_right_enter, R.anim.deceleration_to_right_exit)
			}
		}
	}
}