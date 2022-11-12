package com.cooder.cooder.project.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.cooder.cooder.project.common.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 20:30
 *
 * 介绍：EmptyView
 */
class EmptyView @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
	
	private val icon: TextView
	private val title: TextView
	private val refresh: Button
	
	init {
		orientation = VERTICAL
		gravity = Gravity.CENTER
		LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
		
		icon = findViewById(R.id.empty_icon)
		title = findViewById(R.id.empty_title)
		refresh = findViewById(R.id.empty_refresh)
		
		icon.typeface = Typeface.createFromAsset(context.assets, "fonts/alibaba_iconfont.ttf")
		icon.setText(R.string.empty_view_icon)
	}
	
	/**
	 * 设置图标
	 */
	fun setIcon(icon: String) {
		this.icon.text = icon
	}
	
	/**
	 * 设置图标
	 */
	fun setIcon(@StringRes icon: Int) {
		this.icon.setText(icon)
	}
	
	/**
	 * 设置标题
	 */
	fun setTitle(title: String) {
		this.title.text = title
	}
	
	/**
	 * 设置标题
	 */
	fun setTitle(@StringRes title: Int) {
		this.title.setText(title)
	}
	
	/**
	 * 设置刷新按钮文本
	 */
	fun setRefreshText(text: String) {
		this.refresh.text = text
	}
	
	/**
	 * 设置刷新按钮文本
	 */
	fun setRefreshText(@StringRes text: Int) {
		this.refresh.setText(text)
	}
	
	/**
	 * 设置字体
	 */
	fun setTypeface(typeface: Typeface) {
		icon.typeface = typeface
	}
	
	/**
	 * 隐藏图标
	 */
	fun goneIcon() {
		this.icon.visibility = GONE
	}
	
	/**
	 * 隐藏标题
	 */
	fun goneTitle() {
		this.title.visibility = GONE
	}
	
	/**
	 * 隐藏刷新按钮
	 */
	fun goneRefresh() {
		this.refresh.visibility = GONE
	}
	
	/**
	 * 设置刷新按钮点击事件
	 */
	fun setOnClickRefreshListener(listener: (View) -> Unit) {
		refresh.setOnClickListener(listener)
	}
}