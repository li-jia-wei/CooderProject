package com.cooder.cooder.project.common.ui.view.input

import android.text.Editable
import android.widget.EditText

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 09:18
 *
 * 介绍：输入框最大长度观察者
 */
internal class MaxLengthWatcher(
	private val editText: EditText,
	private val maxLength: Int
) : InputTextWatcher(editText) {
	
	override fun afterTextChanged(s: Editable?) {
		if (s == null) return
		if (s.length > maxLength) {
			editText.setText(s.substring(0, s.lastIndex))
			editText.requestFocus()
			editText.setSelection(s.lastIndex)
		}
	}
}