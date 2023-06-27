package com.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding
import com.cooder.library.library.util.CoDisplayUtil

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 20:45
 *
 * 介绍：CoBaseDialog
 */
abstract class CoBaseDialog<VB : ViewBinding>(
	private val width: Int,
	private val height: Int
) : AppCompatDialogFragment() {
	
	constructor() : this(
		((CoDisplayUtil.getDisplayWidth(CoDisplayUtil.Unit.PX)) * 0.7F).toInt(),
		WindowManager.LayoutParams.WRAP_CONTENT
	)
	
	lateinit var binding: VB
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content)
		binding = getViewBinding(inflater, parent)
		onCreateViewInit(inflater, container, savedInstanceState)
		return binding.root
	}
	
	abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): VB
	
	open fun onCreateViewInit(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
		dialog?.window?.apply {
			setLayout(width, height)
		}
	}
}