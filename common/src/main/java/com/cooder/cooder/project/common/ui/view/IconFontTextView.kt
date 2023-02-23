package com.cooder.cooder.project.common.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.cooder.cooder.project.common.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/16 12:04
 *
 * 介绍：用以支持全局IconFont资源的引用
 */
class IconFontTextView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
	
	private companion object {
		private const val TYPEFACE_DEFAULT = "font/iconfont_default.ttf"
	}
	
	init {
		val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconFontTextView)
		val typefacePath = typedArray.getString(R.styleable.IconFontTextView_typeface) ?: TYPEFACE_DEFAULT
		typedArray.recycle()
		typeface = Typeface.createFromAsset(context.assets, typefacePath)
	}
	
	fun setTypeface(@StringRes pathId: Int) {
		typeface = Typeface.createFromAsset(context.assets, context.getString(pathId))
	}
	
	fun setTypeface(path: String) {
		typeface = Typeface.createFromAsset(context.assets, path)
	}
}