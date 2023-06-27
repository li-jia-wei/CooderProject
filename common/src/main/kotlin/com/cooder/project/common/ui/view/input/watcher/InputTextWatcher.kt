package com.cooder.project.common.ui.view.input.watcher

import android.text.Editable
import android.text.TextWatcher

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 09:03
 *
 * 介绍：输入框观察者
 */
internal abstract class InputTextWatcher : TextWatcher {
	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
	
	}
	
	override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
	
	}
	
	override fun afterTextChanged(s: Editable?) {
	
	}
}