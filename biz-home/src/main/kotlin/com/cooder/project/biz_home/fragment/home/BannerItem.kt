package com.cooder.project.biz_home.fragment.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.banner.CoBanner
import com.cooder.library.ui.banner.core.CoBannerMo
import com.cooder.library.ui.banner.indicator.CircleIndicator
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.ui.view.expends.load
import com.cooder.project.pub_mod.model.HomeBanner

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
	private val bannerList: List<HomeBanner>
) : CoDataItem<List<HomeBanner>, CoViewHolder>() {
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val banner = holder.itemView as CoBanner
		val models = mutableListOf<CoBannerMo>()
		bannerList.forEach {
			models += CoBannerMo(it.cover)
		}
		banner.setBannerData(models)
		banner.setBindAdapter { viewHolder, mo, _ ->
			(viewHolder.rootView as ImageView).load(mo.url)
		}
		banner.setOnBannerClickListener { _, _, position1 ->
			val url = bannerList[position1].url
			CoRoute.startActivityForBrowser(url)
		}
	}
	
	override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
		val context = parent.context
		val banner = CoBanner(context)
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 170.dpInt)
		params.bottomMargin = 14.dpInt
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