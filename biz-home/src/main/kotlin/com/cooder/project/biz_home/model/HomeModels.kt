package com.cooder.project.biz_home.model

import com.cooder.project.pub_mod.model.GoodsModel
import java.io.Serializable

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/2 13:19
 *
 * 介绍：HomeModel
 */
data class HomeMo(
	val bannerList: List<HomeBanner>?,
	val goodsList: List<GoodsModel>?,
	val subcategoryList: List<SubcategoryMo>?
) : Serializable

/**
 * 主页顶部导航栏
 */
data class TabCategoryMo(
	val categoryId: String,
	val categoryName: String,
	val goodsCount: String
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
 * 子类别
 */
data class SubcategoryMo(
	val categoryId: String,
	val groupName: String,
	val showType: String,
	val subcategoryIcon: String,
	val subcategoryId: String,
	val subcategoryName: String
) : Serializable