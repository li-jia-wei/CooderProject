package com.cooder.cooder.project.app.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cooder.cooder.project.app.databinding.FragmentRecommendBinding
import com.cooder.cooder.project.common.ui.component.CoBaseFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 15:42
 *
 * 介绍：推荐Fragment
 */
class RecommendFragment : CoBaseFragment<FragmentRecommendBinding>() {
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecommendBinding {
		return FragmentRecommendBinding.inflate(inflater, container, false)
	}
}