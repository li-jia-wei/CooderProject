package com.cooder.cooder.project.app.main.fragment.home

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.project.app.main.model.HomeBanner
import com.cooder.cooder.ui.banner.CooderBanner
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:06
 *
 * 介绍：BannerItem
 */
class BannerItem(
	bannerList: List<HomeBanner>
) : CooderDataItem<List<HomeBanner>, RecyclerView.ViewHolder>(bannerList) {
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
	
	}
	
	override fun getItemView(parent: ViewGroup): View? {
		val context = parent.context
		val banner = CooderBanner(context)
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 160.dp.toInt())
		banner.layoutParams = params
		banner.setBackgroundColor(Color.WHITE)
		return banner
	}
}