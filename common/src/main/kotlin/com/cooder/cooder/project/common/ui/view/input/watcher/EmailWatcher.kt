package com.cooder.cooder.project.common.ui.view.input.watcher

import android.text.Editable
import android.widget.EditText

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 18:19
 *
 * 介绍：邮箱观察者
 */
internal class EmailWatcher(
	private val editText: EditText
) : InputTextWatcher(editText) {
	
	private companion object {
		private const val EMAIL_MAX_LENGTH = 256
	}
	
	/**
	 * 限制只能输入的字符
	 */
	private val letters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_@."
	
	private var lastLength = 0
	
	override fun afterTextChanged(s: Editable?) {
		if (s == null) return
		if (s.length > EMAIL_MAX_LENGTH) {
			editText.setText(s.substring(0, s.lastIndex))
			editText.requestFocus()
			editText.setSelection(s.lastIndex)
		}
		if (s.isNotEmpty()) {
			if (lastLength < s.length) {
				if (s[s.lastIndex] !in letters) {
					editText.setText(s.substring(0, s.lastIndex))
					editText.requestFocus()
					editText.setSelection(s.lastIndex)
				}
			}
		}
		lastLength = editText.length()
	}
}