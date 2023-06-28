package com.cooder.project.debug.tool.annotation

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/27 23:00
 *
 * 介绍：CoDebug
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CoDebug(val name: String, val hint: String = "", val desc: String = "")
