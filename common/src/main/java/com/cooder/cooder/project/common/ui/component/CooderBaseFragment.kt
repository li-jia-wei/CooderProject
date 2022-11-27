package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cooder.cooder.library.log.CooderLog

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:18
 *
 * 文件介绍：CooderBaseFragment
 */
abstract class CooderBaseFragment : Fragment() {
	
	lateinit var layoutView: View
		private set
	
	@LayoutRes
	protected abstract fun getLayoutId(): Int
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		layoutView = inflater.inflate(getLayoutId(), container, false)
		return layoutView
	}
	
	protected fun isAlive(): Boolean {
		if (isRemoving || isDetached || activity == null) {
			return false
		}
		return true
	}
	
	protected fun showToast(text: String?) {
		CooderLog.i(text)
		Toast.makeText(requireContext(), text ?: "null", Toast.LENGTH_SHORT).show()
	}
	
	protected fun showToast(@StringRes resId: Int) {
		CooderLog.i(getString(resId))
		Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
	}
}