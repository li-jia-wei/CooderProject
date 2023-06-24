package com.cooder.cooder.biz_detail.items

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.cooder.biz_detail.R
import com.cooder.cooder.biz_detail.databinding.ItemDetailShopBinding
import com.cooder.cooder.common.ui.view.expends.load
import com.cooder.cooder.library.log.CoLog
import com.cooder.cooder.pub_mod.items.GoodsItem
import com.cooder.cooder.pub_mod.model.DetailModel
import com.cooder.cooder.pub_mod.model.GoodsModel
import com.cooder.cooder.ui.item.CoAdapter
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/3 18:52
 *
 * 介绍：商品详情 - 店铺
 */
class ShopItem(private val model: DetailModel) : CoDataItem<DetailModel, CoViewHolder>() {

    private lateinit var binding: ItemDetailShopBinding

    private companion object {
        private const val SHOP_GOODS_ITEM_SPAN = 3
    }

    override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
        binding = ItemDetailShopBinding.inflate(inflater, parent, false)
        return binding.root
    }

    override fun onBindData(holder: CoViewHolder, position: Int) {
        val context = holder.context

        // 商品logo
        val shop = model.shop
        binding.shopLogo.load(shop.logo)
        binding.shopTitle.text = shop.name
        binding.shopDesc.text =
            context.getString(R.string.detail_shop_desc, shop.goodsNum, shop.completedNum)

        // 商品标签
        val tags = shop.evaluation.split(' ')
        if (tags.size > 1) {
            binding.tagContainer.visibility = View.VISIBLE
            for (i in 0 until tags.size / 2) {
                val tagView = if (i < binding.tagContainer.childCount) {
                    binding.tagContainer.getChildAt(i) as TextView
                } else {
                    val tag = TextView(context)
                    val params =
                        LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                    params.weight = 1F
                    tag.layoutParams = params
                    tag.gravity = Gravity.CENTER
                    tag.textSize = 14F
                    tag.setTextColor(ContextCompat.getColor(context, R.color.gray))
                    val name = tags[i * 2]
                    val level = tags[i * 2 + 1]
                    tag.text = spanTag(context, name, level)
                    binding.tagContainer.addView(tag)
                    tag
                }
                val name = tags[i * 2]
                val level = tags[i * 2 + 1]
                tagView.text = spanTag(context, name, level)
            }
        }

        // 商品栏
        CoLog.i(model.flowGoods == null)
        model.flowGoods?.let { goods ->
            binding.shopGoods.visibility = View.VISIBLE
            if (binding.shopGoods.adapter == null) {
                binding.shopGoods.layoutManager = GridLayoutManager(context, SHOP_GOODS_ITEM_SPAN)
                binding.shopGoods.adapter = CoAdapter(context)
            }
            val dataItem = mutableListOf<ShopGoodItem>()
            goods.forEach {
                dataItem += ShopGoodItem(it)
            }
            val adapter = binding.shopGoods.adapter as CoAdapter
            adapter.removeAllItems()
            adapter.addItems(dataItem, true)
        }
    }

    private class ShopGoodItem(model: GoodsModel) : GoodsItem(model, false) {

        override fun getItemLayoutRes(): Int {
            return R.layout.item_detail_shop_goods
        }

        override fun onViewAttachedToWindow(holder: GoodsItemViewHolder) {
            super.onViewAttachedToWindow(holder)
            val image: ImageView = holder.findViewById(R.id.item_image)
            val parent = holder.parent
            val availableWidth = parent.measuredWidth - parent.paddingStart - parent.paddingEnd
            val imageWidth = availableWidth / SHOP_GOODS_ITEM_SPAN
            val params = image.layoutParams
            params.width = imageWidth
            params.height = imageWidth
            image.layoutParams = params
        }
    }

    /**
     * 设置tag的富文本
     */
    private fun spanTag(context: Context, name: String, level: String): CharSequence {
        val ssLevel = SpannableString(level)
        ssLevel.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.detail_shop_tag_level_fg
                )
            ), 0, ssLevel.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssLevel.setSpan(
            BackgroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.detail_shop_tag_level_bg
                )
            ), 0, ssLevel.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val ssbTag = SpannableStringBuilder()
        ssbTag.append(name)
        ssbTag.append(ssLevel)
        return ssbTag
    }
}