package com.cooder.cooder.project.app.model

/**
 * UserProfile
 */
data class UserProfile(
	val isLogin: Boolean,
	val favoriteCount: Int,
	val browseCount: Int,
	val learnMinutes: Int,
	val userName: String,
	val userIcon: String,
	val bannerNoticeList: List<Notice>
)

/**
 * Notice
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

/**
 * CourseNotice
 */
data class CourseNotice(
	val total: Int,
	val list: List<Notice>?
)