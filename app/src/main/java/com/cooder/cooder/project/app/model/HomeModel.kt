package com.cooder.cooder.project.app.model

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
	val bannerList: List<HomeBanner>?,
	val goodsList: List<GoodsModel>?,
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

data class GoodsList(
	val total: Int,
	val list: List<GoodsModel>
)