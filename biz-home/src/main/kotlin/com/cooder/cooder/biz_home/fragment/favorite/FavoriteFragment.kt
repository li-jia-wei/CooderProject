package com.cooder.cooder.biz_home.fragment.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cooder.cooder.biz_home.databinding.FragmentFavoriteBinding
import com.cooder.cooder.biz_home.fragment.profile.TestActivity
import com.cooder.cooder.common.ui.component.CoBaseFragment
import com.cooder.cooder.library.log.CoLog

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
        CoLog.i("执行了一遍")
        binding.favorite.setOnClickListener {
            CoLog.i("点击了一下")
            startActivity(Intent(requireContext(), TestActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CoLog.i("destroy")
    }

    override fun onResume() {
        super.onResume()
        CoLog.i("resume")
    }

    override fun onPause() {
        super.onPause()
        CoLog.i("点击了一下")
    }
}