package com.cooder.cooder.project.common.ui.view.input

import android.content.Context
import android.view.ViewGroup
import android.widget.EditText

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 09:08
 *
 * 介绍：输入类型处理者
 */
internal abstract class InputTypeProcessor(
	context: Context,
	viewGroup: ViewGroup,
	editText: EditText
) {
	
	abstract fun process()
}