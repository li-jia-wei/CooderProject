package com.cooder.project.biz_detail.model

import com.cooder.project.pub_mod.model.GoodsModel
import com.cooder.project.pub_mod.model.SliderImage
import java.io.Serializable

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/27 19:21
 *
 * 介绍：DetailModel
 */
data class DetailMo(
	val categoryId: String,
	val commentCountTitle: String,
	val commentModels: List<CommentMo>?,
	val commentTags: String?,
	val completedNumText: String,
	val createTime: String,
	val flowGoods: List<GoodsModel>?,
	val gallery: List<SliderImage>?,
	val goodAttr: List<MutableMap<String, String>>?,
	val goodDescription: String?,
	val goodsId: String,
	val goodsName: String,
	val groupPrice: String,
	val hot: Boolean,
	val isFavorite: Boolean,
	val marketPrice: String,
	val shop: ShopMo,
	val similarGoods: List<GoodsModel>?,
	val sliderImage: String,
	val sliderImages: List<SliderImage>?,
	val tags: String
) : Serializable

data class CommentMo(
	val avatar: String,
	val content: String,
	val nickName: String
) : Serializable

data class ShopMo(
	val completedNum: String,
	val evaluation: String,
	val goodsNum: String,
	val logo: String,
	val name: String
) : Serializable

data class FavoriteMo(val goodsId: String, val isFavorite: Boolean) : Serializable