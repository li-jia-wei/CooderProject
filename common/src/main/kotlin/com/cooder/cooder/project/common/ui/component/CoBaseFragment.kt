package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/22 22:10:10
 *
 * 介绍：CoBaseFragment，已适配ViewBinding
 */
abstract class CoBaseFragment<VB : ViewBinding> : Fragment() {
	
	private var _binding: VB? = null
	
	val binding: VB get() = _binding!!
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_binding = getViewBinding(inflater, container)
		return binding.root
	}
	
	abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
	
	open fun isAlive(): Boolean {
		return !isRemoving && !isDetached && activity != null
	}
	
	open fun isNotAlive(): Boolean {
		return isRemoving || isDetached || activity == null
	}
	
	fun showToast(text: String?) {
		Toast.makeText(requireContext(), text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	fun showToast(@StringRes resId: Int) {
		Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
	}
	
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}