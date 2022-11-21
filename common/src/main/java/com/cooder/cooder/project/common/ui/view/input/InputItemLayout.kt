package com.cooder.cooder.project.common.ui.view.input

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.library.util.sp
import com.cooder.cooder.project.common.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/16 12:40
 *
 * 介绍：InputItemLayout
 */
class InputItemLayout @JvmOverloads constructor(
	context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
	
	private lateinit var editText: EditText
	
	private val topLine: Line
	private val bottomLine: Line
	
	private var topPaint: Paint? = null
	private var bottomPaint: Paint? = null
	
	private val maxLength: Int
	
	private companion object {
		const val TEXT = 0
		const val NUMBER = 1
		const val USERNAME = 2
		const val PASSWORD = 3
		const val PHONE = 4
		const val EMAIL = 5
		
		val DEFAULT_INPUT_FONT_SIZE = 14.sp.toInt()
		val DEFAULT_TITLE_FONT_SIZE = 15.sp.toInt()
		
		val DEFAULT_MIN_WIDTH = 100.dp.toInt()
		
		val DEFAULT_LINE_HEIGHT = 0.5.dp.toInt()
		
		const val DEFAULT_MAX_LENGTH = 20
	}
	
	init {
		orientation = HORIZONTAL
		// 防止不执行onDraw方法
		setWillNotDraw(false)
		
		val array = context.obtainStyledAttributes(attributeSet, R.styleable.InputItemLayout)
		
		// 标题
		val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
		val title = array.getString(R.styleable.InputItemLayout_title)
		parseTitleStyle(titleStyleId, title)
		
		// 输入框
		val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputTextAppearance, 0)
		val hint = array.getString(R.styleable.InputItemLayout_hint)
		val inputType = array.getInteger(R.styleable.InputItemLayout_inputType, TEXT)
		maxLength = array.getInteger(R.styleable.InputItemLayout_maxLength, DEFAULT_MAX_LENGTH)
		
		parseInputStyle(inputStyleId, hint, inputType)
		
		// 上下分割线
		val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
		val bottomLineStyleId = array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
		this.topLine = parseLineStyle(topLineStyleId)
		this.bottomLine = parseLineStyle(bottomLineStyleId)
		
		array.recycle()
		
		initPaint()
	}
	
	/**
	 * 获取输入框数据
	 */
	fun getText(): String {
		return this.editText.text.toString()
	}
	
	/**
	 * 解析左侧标题
	 *
	 * 在parseInputStyle方法之前执行
	 */
	@SuppressLint("CustomViewStyleable")
	private fun parseTitleStyle(resId: Int, title: String?) {
		val array = context.obtainStyledAttributes(resId, R.styleable.TitleTextAppearance)
		val titleColor = array.getColor(R.styleable.TitleTextAppearance_titleColor, ContextCompat.getColor(context, R.color.darker_gray))
		val titleSize = array.getDimensionPixelOffset(R.styleable.TitleTextAppearance_titleSize, DEFAULT_TITLE_FONT_SIZE)
		val minWidth = array.getDimensionPixelOffset(R.styleable.TitleTextAppearance_minWidth, DEFAULT_MIN_WIDTH)
		array.recycle()
		
		val titleView = TextView(context)
		titleView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
		titleView.text = title
		titleView.setTextColor(titleColor)
		titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
		titleView.minWidth = minWidth
		titleView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
		addView(titleView)
	}
	
	/**
	 * 解析右侧输入框
	 *
	 * 在parseTitleStyle方法之后执行
	 */
	@SuppressLint("CustomViewStyleable")
	private fun parseInputStyle(resId: Int, hint: String?, inputType: Int) {
		val array = context.obtainStyledAttributes(resId, R.styleable.InputTextAppearance)
		val hintColor = array.getColor(R.styleable.InputTextAppearance_hintColor, ContextCompat.getColor(context, R.color.gray))
		val inputColor = array.getColor(R.styleable.InputTextAppearance_inputColor, ContextCompat.getColor(context, R.color.darker_gray))
		val textSize = array.getDimensionPixelSize(R.styleable.InputTextAppearance_textSize, DEFAULT_INPUT_FONT_SIZE)
		array.recycle()
		
		editText = EditText(context)
		val editTextParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
		editTextParams.weight = 1F
		editText.layoutParams = editTextParams
		editText.setHintTextColor(hintColor)
		editText.setTextColor(inputColor)
		editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
		editText.hint = hint
		editText.setBackgroundColor(Color.TRANSPARENT)
		editText.gravity = Gravity.START or Gravity.CENTER_VERTICAL
		editText.inputType = when (inputType) {
			TEXT, USERNAME -> InputType.TYPE_CLASS_TEXT
			NUMBER -> InputType.TYPE_CLASS_NUMBER
			PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
			PHONE -> InputType.TYPE_CLASS_PHONE
			EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
			else -> InputType.TYPE_CLASS_TEXT
		}
		addView(editText)
		// 解析TextWatcher
		parseTextWatcher(inputType)
		// 解析输入类型处理
		parseInputTypeProcessable(inputType)
	}
	
	/**
	 * 解析输入框观察者
	 */
	private fun parseTextWatcher(inputType: Int) {
		val watchers = mutableListOf<InputTextWatcher>()
		watchers += MaxLengthWatcher(editText, maxLength)
		when (inputType) {
			USERNAME -> {
				watchers += UsernameWatcher(editText)
			}
		}
		for (watcher in watchers) {
			editText.addTextChangedListener(watcher)
		}
	}
	
	/**
	 * 解析输入类型处理，制作个性化的UI
	 */
	private fun parseInputTypeProcessable(inputType: Int) {
		var processor: InputTypeProcessor? = null
		when (inputType) {
			PASSWORD -> {
				processor = PasswordProcessor(context, this, editText)
			}
		}
		processor?.process()
	}
	
	
	/**
	 * 解析上下分割线
	 */
	@SuppressLint("CustomViewStyleable")
	private fun parseLineStyle(resId: Int): Line {
		val array = context.obtainStyledAttributes(resId, R.styleable.LineAppearance)
		val color = array.getColor(R.styleable.LineAppearance_color, ContextCompat.getColor(context, R.color.lighter_gray))
		val height = array.getDimensionPixelOffset(R.styleable.LineAppearance_height, DEFAULT_LINE_HEIGHT)
		val leftMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_leftMargin, 0)
		val rightMargin = array.getDimensionPixelOffset(R.styleable.LineAppearance_rightMargin, 0)
		val enable = array.getBoolean(R.styleable.LineAppearance_enable, false)
		array.recycle()
		
		return Line(color, height, leftMargin, rightMargin, enable)
	}
	
	/**
	 * 初始化Paint
	 */
	private fun initPaint() {
		if (topLine.enable) {
			topPaint = Paint(Paint.ANTI_ALIAS_FLAG)
			topPaint!!.apply {
				color = topLine.color
				style = Paint.Style.FILL_AND_STROKE
				strokeWidth = topLine.height.toFloat()
			}
		}
		if (bottomLine.enable) {
			bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)
			bottomPaint!!.apply {
				color = topLine.color
				style = Paint.Style.FILL_AND_STROKE
				strokeWidth = bottomLine.height.toFloat()
			}
		}
	}
	
	/**
	 * 分割线
	 */
	private data class Line(val color: Int, val height: Int, val leftMargin: Int, val rightMargin: Int, val enable: Boolean)
	
	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)
		if (topLine.enable) {
			canvas?.drawLine(
				topLine.leftMargin.toFloat(), 0F, (measuredWidth - topLine.rightMargin).toFloat(), 0F, topPaint!!
			)
		}
		if (bottomLine.enable) {
			canvas?.drawLine(
				bottomLine.leftMargin.toFloat(),
				(height - bottomLine.height).toFloat(),
				(measuredWidth - bottomLine.rightMargin).toFloat(),
				(height - bottomLine.height).toFloat(),
				bottomPaint!!
			)
		}
	}
}