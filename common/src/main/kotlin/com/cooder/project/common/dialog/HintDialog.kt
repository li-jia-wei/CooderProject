package com.cooder.project.common.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.cooder.project.common.databinding.DialogHintBinding
import com.cooder.project.common.ui.component.CoBaseDialogFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/7 19:22
 *
 * 介绍：提示框
 */
class HintDialog private constructor(
	private val param: HintDialogParam
) : CoBaseDialogFragment<DialogHintBinding>() {
	
	class Builder(
		private val context: Context
	) {
		private var title: String? = null
		private var subtitle: String? = null
		private var cancel: String? = null
		private var cancelColor: Int = -1
		private var confirm: String? = null
		private var confirmColor: Int = -1
		private var cancelListener: (() -> Unit)? = null
		private var confirmListener: (() -> Unit)? = null
		
		fun setTitle(text: String): Builder {
			this.title = text
			return this
		}
		
		fun setTitle(@StringRes resId: Int): Builder {
			this.title = context.getString(resId)
			return this
		}
		
		fun setSubtitle(text: String): Builder {
			this.subtitle = text
			return this
		}
		
		fun setSubtitle(@StringRes resId: Int): Builder {
			this.subtitle = context.getString(resId)
			return this
		}
		
		fun setCancel(listener: () -> Unit): Builder {
			this.cancelListener = listener
			return this
		}
		
		@JvmOverloads
		fun setCancel(text: String, @ColorInt color: Int = -1, listener: () -> Unit): Builder {
			this.cancel = text
			this.cancelListener = listener
			this.cancelColor = color
			return this
		}
		
		@JvmOverloads
		fun setCancel(@StringRes resId: Int, @ColorRes color: Int = -1, listener: () -> Unit): Builder {
			this.cancel = context.getString(resId)
			this.cancelListener = listener
			this.cancelColor = color
			return this
		}
		
		fun setConfirm(listener: () -> Unit): Builder {
			this.confirmListener = listener
			return this
		}
		
		@JvmOverloads
		fun setConfirm(text: String, @ColorInt color: Int = -1, listener: () -> Unit): Builder {
			this.confirm = text
			this.confirmListener = listener
			this.confirmColor = color
			return this
		}
		
		@JvmOverloads
		fun setConfirm(@StringRes resId: Int, @ColorRes color: Int = -1, listener: () -> Unit): Builder {
			this.confirm = context.getString(resId)
			this.confirmListener = listener
			this.confirmColor = context.getColor(color)
			return this
		}
		
		fun show(): HintDialog {
			val param = HintDialogParam(title, subtitle, cancel, cancelColor, confirm, confirmColor, cancelListener, confirmListener)
			val dialog = HintDialog(param)
			dialog.showNow((context as FragmentActivity).supportFragmentManager, "HintDialogTag")
			return dialog
		}
	}
	
	private data class HintDialogParam(
		val title: String?,
		val subtitle: String?,
		val cancel: String?,
		@ColorInt val cancelColor: Int,
		val confirm: String?,
		@ColorInt val confirmColor: Int,
		val cancelListener: (() -> Unit)?,
		val confirmListener: (() -> Unit)?
	)
	
	override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): DialogHintBinding {
		return DialogHintBinding.inflate(inflater, parent, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		isCancelable = false
		
		if (!param.title.isNullOrBlank()) {
			binding.title.text = param.title
		}
		
		if (!param.subtitle.isNullOrBlank()) {
			binding.subtitle.visibility = View.VISIBLE
			binding.subtitle.text = param.subtitle
		}
		
		if (!param.cancel.isNullOrBlank()) {
			binding.cancel.text = param.cancel
		}
		
		if (param.cancelColor != -1) {
			binding.cancel.setTextColor(param.cancelColor)
		}
		
		if (!param.confirm.isNullOrBlank()) {
			binding.confirm.text = param.confirm
		}
		
		if (param.confirmColor != -1) {
			binding.confirm.setTextColor(param.confirmColor)
		}
		
		binding.cancel.setOnClickListener {
			dialog?.cancel()
			param.cancelListener?.invoke()
		}
		
		binding.confirm.setOnClickListener {
			dialog?.cancel()
			param.confirmListener?.invoke()
		}
	}
}