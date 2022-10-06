package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:18
 *
 * 文件介绍：CooderBaseFragment
 */
abstract class CooderBaseFragment : Fragment(), FindViewById {
	
	lateinit var layoutView: View private set
	
	@LayoutRes
	abstract fun getLayoutId(): Int
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		layoutView = inflater.inflate(getLayoutId(), container, false)
		return layoutView
	}
	
	override fun <T : View> findViewById(id: Int): T {
		return layoutView.findViewById(id)
	}
}