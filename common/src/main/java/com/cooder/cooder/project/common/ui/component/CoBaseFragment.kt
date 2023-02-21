package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 14:18
 *
 * 介绍：CoBaseFragment
 */
abstract class CoBaseFragment : Fragment() {
	
	private val viewCaches = SparseArray<View>()
	
	lateinit var root: View
		private set
	
	@LayoutRes
	protected abstract fun getLayoutId(): Int
	
	@CallSuper
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		root = inflater.inflate(getLayoutId(), container, false)
		return root
	}
	
	/**
	 * 判断是否存活
	 */
	protected open fun isAlive(): Boolean {
		return !isNotAlive()
	}
	
	/**
	 * 判断是否不存活
	 */
	protected open fun isNotAlive(): Boolean {
		return isRemoving || isDetached || activity == null
	}
	
	protected fun showToast(text: String?) {
		Toast.makeText(requireContext(), text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	@Suppress("UNCHECKED_CAST")
	fun <T : View> findViewById(@IdRes id: Int): T {
		var view = viewCaches[id]
		if (view == null) {
			view = root.findViewById(id)
			viewCaches[id] = view
		}
		return view as T
	}
	
	fun addView(view: View) {
		(root as ViewGroup).addView(view)
	}
}