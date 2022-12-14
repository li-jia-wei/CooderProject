package com.cooder.cooder.project.app.main.model

/**
 * 主页顶部导航栏
 */
data class TabCategory(
	val categoryId: String,
	val categoryName: String,
	val goodsCount: String
)

/**
 * 主页模型
 */
data class HomeModel(
	val homeBannerList: List<HomeBanner>?,
	val homeGoodsList: List<HomeGoods>?,
	val subcategoryList: List<Subcategory>?
)

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
)

/**
 * 商品列表
 */
data class HomeGoods(
	val categoryId: String,
	val completedNumText: String,
	val createTime: String,
	val goodsId: String,
	val goodsName: String,
	val groupPrice: String,
	val hot: Boolean,
	val joinedAvatars: Any,
	val marketPrice: String,
	val sliderImage: String,
	val sliderImages: List<SliderImage>,
	val tags: String
)

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
)

data class SliderImage(
	val type: Int,
	val url: String
)