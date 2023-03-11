package com.cooder.cooder.debug.tool.annotation

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/27 23:00
 *
 * 介绍：CoDebug
 *
 * @param id 排序序号，按照从小到大排序
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CoDebug(val name: String, val hint: String, val desc: String)
