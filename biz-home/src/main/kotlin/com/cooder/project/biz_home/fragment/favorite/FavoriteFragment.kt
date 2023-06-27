package com.cooder.project.biz_home.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cooder.project.biz_home.databinding.FragmentFavoriteBinding
import com.cooder.project.common.ui.component.CoBaseFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/4 15:38
 *
 * 介绍：喜欢Fragment
 */
class FavoriteFragment : CoBaseFragment<FragmentFavoriteBinding>() {
	
	private val viewModel by lazy {
		ViewModelProvider(this)[FavoriteViewModel::class.java]
	}
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoriteBinding {
		return FragmentFavoriteBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}
}