package com.cooder.cooder.project.app.main.model

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/24 21:49
 *
 * 介绍：UserProfile
 */
data class UserProfile(
	val isLogin: Boolean,
	val favoriteCount: Int,
	val browseCount: Int,
	val learnMinutes: Int,
	val userName: String,
	val avatar: String,
	val bannerNoticeList: List<Notice>
)