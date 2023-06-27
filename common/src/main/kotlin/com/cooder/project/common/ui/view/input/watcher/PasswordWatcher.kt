package com.cooder.project.common.ui.view.input.watcher

import android.text.Editable
import android.widget.EditText

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 09:16
 *
 * 介绍：密码观察者
 */
internal class PasswordWatcher(
	private val editText: EditText
) : InputTextWatcher() {
	
	private companion object {
		private const val PASSWORD_MAX_LENGTH = 20
	}
	
	override fun afterTextChanged(s: Editable?) {
		if (s == null) return
		if (s.length > PASSWORD_MAX_LENGTH || (s.isNotEmpty() && s[s.lastIndex].code !in ' '.code..'~'.code)) {
			editText.setText(s.substring(0, s.lastIndex))
			editText.requestFocus()
			editText.setSelection(s.lastIndex)
		}
	}
}