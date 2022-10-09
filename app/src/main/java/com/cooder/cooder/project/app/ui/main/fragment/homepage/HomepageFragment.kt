package com.cooder.cooder.project.app.ui.main.fragment.homepage

import android.os.Bundle
import android.view.View
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.ui.main.fragment.homepage.logic.HomepageFragmentLogic
import com.cooder.cooder.project.common.ui.component.CooderBaseFragment

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 23:40
 *
 * 文件介绍：主页Fragment
 */
class HomepageFragment : CooderBaseFragment(), HomepageFragmentLogic.FragmentProvider {
	
	private lateinit var fragmentLogic: HomepageFragmentLogic
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_homepage
	}
	
	override fun onCreateView(savedInstanceState: Bundle?) {
		fragmentLogic = HomepageFragmentLogic(this, savedInstanceState)
	}
	
	override fun <T : View> findViewById(id: Int): T {
		return layoutView.findViewById(id)
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		fragmentLogic.onSaveInstanceState(outState)
	}
}