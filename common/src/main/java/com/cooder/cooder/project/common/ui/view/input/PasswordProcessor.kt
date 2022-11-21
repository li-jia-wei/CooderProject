package com.cooder.cooder.project.common.ui.view.input

import android.content.Context
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.cooder.cooder.project.common.R
import com.cooder.cooder.project.common.ui.view.IconFontTextView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/21 09:10
 *
 * 介绍：密码处理者
 */
internal class PasswordProcessor(
	private val context: Context,
	private val viewGroup: ViewGroup,
	private val editText: EditText
) : InputTypeProcessor(context, viewGroup, editText) {
	
	private var hidden = true
	
	@StringRes
	private val visible = R.string.ic_visible
	
	@StringRes
	private val notVisible = R.string.ic_not_visible
	
	override fun process() {
		val visibleView = IconFontTextView(context)
		visibleView.textSize = 25F
		visibleView.setTypeface(R.string.ic_alibaba_path)
		visibleView.setText(notVisible)
		visibleView.gravity = Gravity.CENTER
		visibleView.setTextColor(ContextCompat.getColor(context, R.color.darker_gray))
		visibleView.setOnClickListener {
			hidden = !hidden
			if (hidden) {
				visibleView.setText(notVisible)
				editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
			} else {
				visibleView.setText(visible)
				editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
			}
			editText.setSelection(editText.length())
		}
		val visibleParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
		visibleParams.gravity = Gravity.CENTER_VERTICAL
		viewGroup.addView(visibleView, visibleParams)
	}
}