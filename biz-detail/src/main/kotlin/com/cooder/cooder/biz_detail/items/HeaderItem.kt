package com.cooder.cooder.biz_detail.items

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.cooder.cooder.biz_detail.databinding.ItemDetailHeaderBinding
import com.cooder.cooder.common.ui.view.expends.load
import com.cooder.cooder.pub_mod.model.DetailModel
import com.cooder.cooder.pub_mod.model.SliderImage
import com.cooder.cooder.ui.banner.core.CoBannerMo
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/28 12:23
 *
 * 介绍：商品详情 - 顶部轮播图
 */
class HeaderItem(
    private val sliderImages: List<SliderImage>?,
    private val price: String,
    private val completedNumText: String?,
    private val goodsName: String?
) : CoDataItem<DetailModel, CoViewHolder>() {

    private lateinit var binding: ItemDetailHeaderBinding

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
        binding = ItemDetailHeaderBinding.inflate(inflater, parent, false)
        return binding.root
    }

    override fun onBindData(holder: CoViewHolder, position: Int) {
        val bannerMos = arrayListOf<CoBannerMo>()
        sliderImages?.forEach { sliderImage ->
            sliderImage.url?.let {
                bannerMos += CoBannerMo(it)
            }
        }
        binding.banner.setBannerData(bannerMos)
        binding.banner.setBindAdapter { viewHolder, mo, _ ->
            val imageView = viewHolder.rootView as ImageView
            imageView.load(mo.url)
        }
        binding.price.text = spanPrice(this.price)
        binding.saleDesc.text = completedNumText
        binding.title.text = goodsName
    }

    private fun spanPrice(price: String): CharSequence {
        if (price.isEmpty()) return ""
        val ss = SpannableString(price)
        ss.setSpan(AbsoluteSizeSpan(18, true), 1, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return ss
    }

    override fun getSpanSize(): Int {
        return 2
    }
}