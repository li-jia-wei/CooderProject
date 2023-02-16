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
	
	init {
		val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.IconFontTextView)
		var typefaceFilename = typedArray.getString(R.styleable.IconFontTextView_typeface)
		if (typefaceFilename != null) {
			if (!isFontFile(typefaceFilename)) {
				typefaceFilename = null
			}
		}
		if (typefaceFilename == null) {
			typefaceFilename = getFirstFontFile(context)
		}
		if (typefaceFilename != null) {
			typeface = Typeface.createFromAsset(context.assets, typefaceFilename)
		}
		typedArray.recycle()
	}
	
	fun setTypeface(@StringRes pathId: Int) {
		typeface = Typeface.createFromAsset(context.assets, context.getString(pathId))
	}
	
	fun setTypeface(path: String) {
		typeface = Typeface.createFromAsset(context.assets, path)
	}
	
	/**
	 * 获取第一个字体文件
	 */
	private fun getFirstFontFile(context: Context): String? {
		val ttfs = context.assets.list("font")
		if (ttfs != null) {
			for (ttf in ttfs) {
				if (isFontFile(ttf)) {
					return "font/$ttf"
				}
			}
		}
		return null
	}
	
	/**
	 * 查询是否是字体文件
	 */
	private fun isFontFile(filename: String): Boolean {
		val fontTypes = listOf(".ttf", ".ttc")
		for (type in fontTypes) {
			if (filename.endsWith(type)) {
				return true
			}
		}
		return false
	}
}