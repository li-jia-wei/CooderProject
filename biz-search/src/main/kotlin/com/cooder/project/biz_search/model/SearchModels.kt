package com.cooder.project.biz_search.model


data class QuickSearchListMo(
	val list: List<KeyWordMo>,
	val total: Int
)

data class KeyWordMo(
	val id: String,
	val keyWord: String
)