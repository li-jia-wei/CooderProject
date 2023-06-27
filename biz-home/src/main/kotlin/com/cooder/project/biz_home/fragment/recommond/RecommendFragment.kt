package com.cooder.project.biz_home.fragment.recommond

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cooder.project.biz_home.databinding.FragmentRecommendBinding
import com.cooder.project.common.ui.component.CoBaseFragment

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
	
	private val viewModel by lazy {
		ViewModelProvider(this)[RecommendViewModel::class.java]
	}
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecommendBinding {
		return FragmentRecommendBinding.inflate(inflater, container, false)
	}
}