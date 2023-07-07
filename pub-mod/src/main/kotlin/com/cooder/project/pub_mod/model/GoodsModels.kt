package com.cooder.project.pub_mod.model

import java.io.Serializable

/**
 * 商品列表
 */
data class GoodsMo(
	val categoryId: String,
	val completedNumText: String,
	val createTime: String,
	val goodsId: String,
	val goodsName: String,
	val groupPrice: String,
	val hot: Boolean,
	val joinedAvatars: List<SliderImage>?,
	val marketPrice: String,
	val sliderImage: String,
	val sliderImages: List<SliderImage>?,
	val tags: String?
) : Serializable

data class GoodsListMo(
	val total: Int,
	val list: List<GoodsMo>
) : Serializable

data class SliderImage(
	val type: Int,
	val url: String?
) : Serializable

/**
 * 选择使用哪个金额
 */
fun selectPrice(groupPrice: String?, marketPrice: String?): String {
	return if (!marketPrice.isNullOrBlank()) {
		if (marketPrice.startsWith("¥")) marketPrice else "¥$marketPrice"
	} else if (!groupPrice.isNullOrBlank()) {
		if (groupPrice.startsWith("¥")) groupPrice else "¥$groupPrice"
	} else {
		"¥未知"
	}
}