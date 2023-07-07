package com.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding
import com.cooder.library.library.util.CoDisplayUtil
import com.cooder.project.common.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 20:45
 *
 * 介绍：CoBaseDialog
 */
abstract class CoBaseDialogFragment<VB : ViewBinding> @JvmOverloads constructor(
	private val width: Int = (CoDisplayUtil.getDisplayWidth(CoDisplayUtil.Unit.PX) * 0.7F).toInt(),
	private val height: Int = WindowManager.LayoutParams.WRAP_CONTENT
) : AppCompatDialogFragment() {
	
	private var _binding: VB? = null
	protected val binding get() = _binding!!
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content)
		_binding = getViewBinding(inflater, parent)
		return binding.root
	}
	
	@CallSuper
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		dialog?.window?.apply {
			this.setLayout(width, height)
		}
		dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_hint_dialog)
	}
	
	abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): VB
}