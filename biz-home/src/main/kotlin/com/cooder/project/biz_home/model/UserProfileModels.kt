package com.cooder.project.biz_home.model

import java.io.Serializable

/**
 * UserProfile
 */
data class UserProfileMo(
	val isLogin: Boolean,
	val favoriteCount: Int,
	val browseCount: Int,
	val learnMinutes: Int,
	val userName: String,
	val userIcon: String,
	val bannerNoticeList: List<NoticeMo>
) : Serializable

/**
 * Notice
 */
data class NoticeMo(
	val id: String,
	val sticky: Int,
	val type: String,
	val title: String,
	val subtitle: String,
	val url: String,
	val cover: String,
	val createTime: String
) : Serializable

/**
 * CourseNotice
 */
data class CourseNoticeMo(
	val total: Int,
	val list: List<NoticeMo>?
) : Serializable