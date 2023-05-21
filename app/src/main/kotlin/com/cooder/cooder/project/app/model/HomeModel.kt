package com.cooder.cooder.project.app.model

import java.io.Serializable

/**
 * 主页顶部导航栏
 */
data class TabCategory(
	val categoryId: String,
	val categoryName: String,
	val goodsCount: String
) : Serializable

/**
 * 主页模型
 */
data class HomeModel(
	val bannerList: List<HomeBanner>?,
	val goodsList: List<GoodsModel>?,
	val subcategoryList: List<Subcategory>?
) : Serializable

/**
 * 主页Banner
 */
data class HomeBanner(
	val cover: String,
	val createTime: String,
	val id: String,
	val sticky: Int,
	val subtitle: String,
	val title: String,
	val type: String,
	val url: String
) : Serializable

/**
 * 商品列表
 */
data class GoodsModel(
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
	val tags: String
) : Serializable

/**
 * 子类别
 */
data class Subcategory(
	val categoryId: String,
	val groupName: String,
	val showType: String,
	val subcategoryIcon: String,
	val subcategoryId: String,
	val subcategoryName: String
) : Serializable

data class SliderImage(
	val type: Int,
	val url: String?
) : Serializable

data class GoodsList(
	val total: Int,
	val list: List<GoodsModel>
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