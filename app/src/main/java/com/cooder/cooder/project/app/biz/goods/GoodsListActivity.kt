package com.cooder.cooder.project.app.biz.goods

import android.os.Bundle
import com.cooder.cooder.project.app.databinding.ActivityGoodsListBinding
import com.cooder.cooder.project.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 12:25
 *
 * 介绍：GoodsListActivity
 */
class GoodsListActivity : CoBaseActivity() {
	
	private lateinit var binding: ActivityGoodsListBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityGoodsListBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}