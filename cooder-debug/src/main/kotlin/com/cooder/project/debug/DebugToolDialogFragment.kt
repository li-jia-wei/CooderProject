package com.cooder.project.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.ui.item.CoViewBindingHolder
import com.cooder.project.common.ui.component.CoBaseDialogFragment
import com.cooder.project.debug.annotation.Debug
import com.cooder.project.debug.annotation.Order
import com.cooder.project.debug.databinding.FragmentDebugToolDialogBinding
import com.cooder.project.debug.databinding.ItemDebugToolBinding
import com.cooder.project.debug.tools.DebugTools
import java.lang.reflect.Method
import com.cooder.library.ui.R as RUi

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 20:41
 *
 * 介绍：Debug工具对话框
 */
class DebugToolDialogFragment : CoBaseDialogFragment<FragmentDebugToolDialogBinding>() {
	
	private val debugTools = arrayOf(DebugTools::class.java)
	override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): FragmentDebugToolDialogBinding {
		return FragmentDebugToolDialogBinding.inflate(inflater, parent, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		val functions = mutableListOf<DebugFunction>()
		debugTools.forEach {
			val target = it.getConstructor().newInstance()
			target.javaClass.declaredMethods.forEach { method ->
				var order: Int = Int.MAX_VALUE
				var title = ""
				var desc = ""
				var hint = ""
				var enable = false
				val debugAnnotation = method.getAnnotation(Debug::class.java)
				val orderAnnotation = method.getAnnotation(Order::class.java)
				if (debugAnnotation != null) {
					title = debugAnnotation.name
					desc = debugAnnotation.desc
					hint = debugAnnotation.hint
					enable = true
				} else {
					if (method.returnType == String::class.java) {
						method.isAccessible = true
						title = method.invoke(target) as String
					}
				}
				if (orderAnnotation != null) {
					order = orderAnnotation.order
				}
				if (title.isNotEmpty()) {
					functions += DebugFunction(order, title, hint, desc, method, enable, target)
				}
			}
		}
		functions.sortBy { it.order }
		
		val dividerItemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
		dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(view.context, R.drawable.shape_debug_divider)!!)
		binding.recyclerView.addItemDecoration(dividerItemDecoration)
		
		binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		binding.recyclerView.adapter = DebugToolAdapter(functions)
	}
	
	data class DebugFunction(
		val order: Int,
		val name: String,
		val warn: String,
		val desc: String,
		val method: Method,
		val enable: Boolean,
		val target: Any
	) {
		
		fun invoke() {
			method.invoke(target)
		}
	}
	
	inner class DebugToolAdapter(
		private val functions: List<DebugFunction>
	) : RecyclerView.Adapter<CoViewBindingHolder<ItemDebugToolBinding>>() {
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoViewBindingHolder<ItemDebugToolBinding> {
			return CoViewBindingHolder(ItemDebugToolBinding.inflate(LayoutInflater.from(parent.context), parent, false))
		}
		
		override fun getItemCount(): Int {
			return functions.size
		}
		
		override fun onBindViewHolder(holder: CoViewBindingHolder<ItemDebugToolBinding>, position: Int) {
			val binding = holder.binding
			val function = functions[position]
			binding.title.text = function.name
			if (function.desc.isNotBlank()) {
				binding.desc.text = function.desc
				binding.desc.visibility = View.VISIBLE
			} else {
				binding.desc.visibility = View.GONE
			}
			if (function.warn.isNotBlank()) {
				binding.warn.text = function.warn
				binding.warn.visibility = View.VISIBLE
			} else {
				binding.warn.visibility = View.GONE
			}
			if (function.enable) {
				binding.root.setOnClickListener {
					function.invoke()
					this@DebugToolDialogFragment.dialog?.cancel()
				}
				binding.icon.setText(RUi.string.ic_tool)
			} else {
				binding.icon.setText(RUi.string.ic_settings)
			}
		}
	}
}