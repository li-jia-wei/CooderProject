package com.cooder.cooder.project.common.ui.component.annotation

import androidx.annotation.IntRange

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/29 20:37
 *
 * 介绍：设置同一个Activity的最大同时存活数量
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AliveCount(@IntRange(from = 1) val count: Int)