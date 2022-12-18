package com.cooder.cooder.project.app.main.fragment.home

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.project.app.main.model.HomeBanner
import com.cooder.cooder.project.app.main.route.CooderRoute
import com.cooder.cooder.project.common.ui.view.expends.load
import com.cooder.cooder.ui.banner.CooderBanner
import com.cooder.cooder.ui.banner.core.CooderBannerMo
import com.cooder.cooder.ui.banner.indicator.CircleIndicator
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
		val banner = holder.itemView as CooderBanner
		val models = mutableListOf<CooderBannerMo>()
		data.forEach {
			models += CooderBannerMo(it.cover)
		}
		banner.setBannerData(models)
		banner.setBindAdapter { viewHolder, mo, _ ->
			(viewHolder.rootView as ImageView).load(mo.url)
		}
		banner.setOnBannerClickListener { _, _, position1 ->
			val url = data[position1].url
			CooderRoute.startActivityForBrowser(url)
		}
	}
	
	override fun getItemView(parent: ViewGroup): View {
		val context = parent.context
		val banner = CooderBanner(context)
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 160.dp.toInt())
		params.bottomMargin = 14.dp.toInt()
		banner.setBackgroundColor(Color.WHITE)
		banner.layoutParams = params
		banner.setBannerIndicator(CircleIndicator(context, size = CircleIndicator.SMALL))
		banner.setAutoPlay(true)
		banner.setIntervalTime(6000)
		banner.setLoop(true)
		banner.setScrollDuration(500)
		return banner
	}
}