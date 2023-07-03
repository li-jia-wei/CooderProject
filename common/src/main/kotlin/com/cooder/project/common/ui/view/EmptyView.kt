package com.cooder.project.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.cooder.library.ui.view.IconFontTextView
import com.cooder.project.common.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 20:30
 *
 * 介绍：空视图
 */
class EmptyView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private val imageView: ImageView
	private val iconView: IconFontTextView
	private val titleView: TextView
	private val descView: TextView
	private val helpActionView: IconFontTextView
	private val refreshButton: Button
	
	init {
		orientation = VERTICAL
		gravity = Gravity.CENTER
		
		LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
		
		imageView = findViewById(R.id.empty_image)
		iconView = findViewById(R.id.empty_icon)
		titleView = findViewById(R.id.empty_title)
		descView = findViewById(R.id.empty_desc)
		helpActionView = findViewById(R.id.empty_help_action)
		refreshButton = findViewById(R.id.empty_refresh_button)
	}
	
	/**
	 * 设置图片
	 */
	fun setImage(@DrawableRes resId: Int) {
		this.imageView.setImageResource(resId)
		this.imageView.visibility = View.VISIBLE
		this.iconView.visibility = View.GONE
	}
	
	/**
	 * 设置图标字体
	 */
	@JvmOverloads
	fun setIcon(@StringRes resId: Int, @ColorRes colorId: Int = -1) {
		this.iconView.setText(resId)
		this.iconView.visibility = View.VISIBLE
		if (colorId != -1) {
			this.iconView.setTextColor(ContextCompat.getColor(context, colorId))
		}
		this.imageView.visibility = View.GONE
	}
	
	/**
	 * 设置标题
	 */
	fun setTitle(@StringRes resId: Int) {
		this.titleView.setText(resId)
		this.titleView.visibility = View.VISIBLE
	}
	
	/**
	 * 设置标题
	 */
	fun setTitle(text: String) {
		this.titleView.text = text
		this.titleView.visibility = View.VISIBLE
	}
	
	/**
	 * 设置刷新按钮文字
	 */
	fun setRefreshButton(@StringRes resId: Int) {
		this.refreshButton.setText(resId)
	}
	
	/**
	 * 设置刷新按钮文字
	 */
	fun setRefreshButton(text: String) {
		this.refreshButton.text = text
	}
	
	/**
	 * 设置按钮点击事件
	 */
	fun setOnClickRefreshListener(listener: OnClickListener) {
		this.refreshButton.setOnClickListener(listener)
	}
	
	/**
	 * 隐藏刷新按钮
	 */
	fun goneRefreshButton() {
		this.refreshButton.visibility = View.GONE
	}
	
	/**
	 * 设置描述
	 */
	fun setDesc(@StringRes resId: Int) {
		this.descView.setText(resId)
	}
	
	/**
	 * 设置描述
	 */
	fun setDesc(text: String) {
		this.descView.text = text
	}
	
	/**
	 * 设置帮助事件
	 */
	fun setOnClickHelpActionListener(listener: OnClickListener) {
		this.helpActionView.setOnClickListener(listener)
		this.helpActionView.visibility = View.VISIBLE
	}
}