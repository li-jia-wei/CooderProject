package com.cooder.cooder.project.app.main.model

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/26 14:23
 *
 * 介绍：CourseNotice
 */
data class CourseNotice(
	val total: Int,
	val list: List<Notice>?
)