package com.cooder.project.biz_search.fragment.empty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cooder.project.biz_search.databinding.FragmentEmptySearchBinding
import com.cooder.project.common.ui.component.CoBaseFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/4 14:09
 *
 * 介绍：当搜索不到内容时显示
 */
class EmptySearchFragment : CoBaseFragment<FragmentEmptySearchBinding>() {
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentEmptySearchBinding {
		return FragmentEmptySearchBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
	}
}