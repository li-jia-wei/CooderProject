package com.cooder.project.biz_search.fragment.history

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/9 12:31
 *
 * 介绍：历史搜索页实现
 */
interface IHistorySearch {
	
	fun doQueryGoodsSearch(keyWord: String, isInit: Boolean)
}