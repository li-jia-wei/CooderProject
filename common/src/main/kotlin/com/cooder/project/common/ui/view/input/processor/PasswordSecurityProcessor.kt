package com.cooder.project.common.ui.view.input.processor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import com.cooder.library.library.util.expends.dp
import com.cooder.library.ui.view.IconFontTextView
import com.cooder.project.common.R
import java.util.regex.Pattern

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/22 09:23
 *
 * 介绍：密码安全处理
 */
internal class PasswordSecurityProcessor(
	private val context: Context,
	private val viewGroup: ViewGroup,
	private val editText: EditText
) : PasswordProcessor(context, viewGroup, editText) {
	
	private val patternDigit = Pattern.compile("[0-9]")
	private val patternLowercase = Pattern.compile("[a-z]")
	private val patternUppercase = Pattern.compile("[A-Z]")
	private val patternSymbol = Pattern.compile("[^0-9a-zA-Z]")
	
	override fun process() {
		super.process()
		val complexityLayout = LinearLayout(context)
		complexityLayout.setBackgroundResource(R.drawable.complexity_background)
		complexityLayout.orientation = LinearLayout.VERTICAL
		complexityLayout.setPadding(0.5.dp.toInt())
		val complexityParams = LinearLayout.LayoutParams(6.dp.toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
		complexityParams.marginStart = 12.dp.toInt()
		complexityParams.topMargin = 5.dp.toInt()
		complexityParams.bottomMargin = 5.dp.toInt()
		val easyView = View(context)
		easyView.visibility = View.INVISIBLE
		easyView.setBackgroundResource(R.drawable.complexity_easy)
		val commonView = View(context)
		commonView.visibility = View.INVISIBLE
		commonView.setBackgroundResource(R.drawable.complexity_common)
		val complexView = View(context)
		complexView.visibility = View.INVISIBLE
		complexView.setBackgroundResource(R.drawable.complexity_complex)
		val safeView = View(context)
		safeView.visibility = View.INVISIBLE
		safeView.setBackgroundResource(R.drawable.complexity_safe)
		val viewParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
		viewParams.bottomMargin = 0.8.dp.toInt()
		viewParams.topMargin = 0.8.dp.toInt()
		viewParams.weight = 1F
		complexityLayout.addView(safeView, viewParams)
		complexityLayout.addView(complexView, viewParams)
		complexityLayout.addView(commonView, viewParams)
		complexityLayout.addView(easyView, viewParams)
		viewGroup.addView(complexityLayout, complexityParams)
		complexityLayout.visibility = View.GONE
		editText.addTextChangedListener {
			if (it == null) return@addTextChangedListener
			if (it.isEmpty()) {
				easyView.visibility = View.INVISIBLE
				commonView.visibility = View.INVISIBLE
				complexView.visibility = View.INVISIBLE
				safeView.visibility = View.INVISIBLE
				return@addTextChangedListener
			}
			val complex = complexJudge(it.toString())
			easyView.visibility = if (complex >= 1) View.VISIBLE else View.INVISIBLE
			commonView.visibility = if (complex >= 2 && it.length >= 8) View.VISIBLE else View.INVISIBLE
			complexView.visibility = if (complex >= 3 && it.length >= 10) View.VISIBLE else View.INVISIBLE
			safeView.visibility = if (complex >= 4 && it.length >= 10) View.VISIBLE else View.INVISIBLE
		}
		
		editText.setOnFocusChangeListener { _, hasFocus ->
			super.setVisibleViewVisible(viewGroup.getChildAt(2) as IconFontTextView, hasFocus)
			if (hasFocus) {
				complexityLayout.visibility = View.VISIBLE
			} else {
				complexityLayout.visibility = View.GONE
			}
		}
		
		// 初始化的时候的状态
		val text = editText.text.toString()
		val complex = complexJudge(editText.text.toString())
		easyView.visibility = if (complex >= 1) View.VISIBLE else View.INVISIBLE
		commonView.visibility = if (complex >= 2 && text.length >= 8) View.VISIBLE else View.INVISIBLE
		complexView.visibility = if (complex >= 3 && text.length >= 10) View.VISIBLE else View.INVISIBLE
		safeView.visibility = if (complex >= 4 && text.length >= 10) View.VISIBLE else View.INVISIBLE
	}
	
	/**
	 * 复杂度判断
	 * @return 复杂度: 1 简单, 2 普通, 3 复杂, 4 安全
	 */
	private fun complexJudge(str: String): Int {
		var result = 0x0        // 0000
		val digit = 0x1          // 0001
		val lowercase = 0x2        // 0010
		val uppercase = 0x4       // 0100
		val symbol = 0x8          // 1000
		for (c in str) {
			val s = c.toString()
			if (patternDigit.matcher(s).matches()) {
				result = result or digit
			} else if (patternLowercase.matcher(s).matches()) {
				result = result or lowercase
			} else if (patternUppercase.matcher(s).matches()) {
				result = result or uppercase
			} else if (patternSymbol.matcher(s).matches()) {
				result = result or symbol
			}
		}
		// 计算有多少个1
		var sum = 0
		while (result != 0) {
			sum++
			result = result and (result - 1)
		}
		return sum
	}
}