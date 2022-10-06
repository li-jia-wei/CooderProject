package com.cooder.cooder.project.common.ui.component

import android.view.View
import androidx.annotation.IdRes

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/5 20:41
 *
 * 文件介绍：FindViewById
 */
interface FindViewById {
	
	fun <T : View> findViewById(@IdRes id: Int): T
}