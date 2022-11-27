package com.cooder.cooder.project.app.main.model

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/26 14:30
 *
 * 介绍：Notice
 */
data class Notice(
	val id: String,
	val sticky: Int,
	val type: String,
	val title: String,
	val subtitle: String,
	val url: String,
	val cover: String,
	val createTime: String
)