package com.cooder.cooder.debug.tool

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.debug.R
import com.cooder.cooder.debug.databinding.FragmentCoDebugToolDialogBinding
import com.cooder.cooder.debug.tool.annotation.CoDebug
import com.cooder.cooder.debug.tool.annotation.CoOrder
import com.cooder.cooder.debug.tool.tools.DebugTools
import com.cooder.cooder.project.common.ui.component.CoBaseDialog
import java.lang.reflect.Method

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/26 20:41
 *
 * 介绍：DebugToolDialogFragment
 */
class CoDebugToolDialogFragment : CoBaseDialog<FragmentCoDebugToolDialogBinding>() {
	
	private val debugTools = arrayOf(DebugTools::class.java)
	override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): FragmentCoDebugToolDialogBinding {
		return FragmentCoDebugToolDialogBinding.inflate(inflater, parent, false)
	}
	
	override fun onCreateViewInit(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
		super.onCreateViewInit(inflater, container, savedInstanceState)
		dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_co_debug_tool)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		val itemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
		itemDecoration.setDrawable(ContextCompat.getDrawable(view.context, R.drawable.shape_co_debug_divider)!!)
		
		val functions = mutableListOf<DebugFunction>()
		debugTools.forEach {
			val target = it.getConstructor().newInstance()
			target.javaClass.declaredMethods.forEach { method ->
				var order: Int = Int.MAX_VALUE
				val title: String
				var desc = ""
				var hint = ""
				var enable = false
				val debugAnnotation = method.getAnnotation(CoDebug::class.java)
				val orderAnnotation = method.getAnnotation(CoOrder::class.java)
				if (debugAnnotation != null) {
					title = debugAnnotation.name
					desc = debugAnnotation.desc
					hint = debugAnnotation.hint
					enable = true
				} else {
					method.isAccessible = true
					title = method.invoke(target) as String
				}
				if (orderAnnotation != null) {
					order = orderAnnotation.order
				}
				functions += DebugFunction(order, title, hint, desc, method, enable, target)
			}
		}
		functions.sortBy { it.order }
		binding.recyclerView.addItemDecoration(itemDecoration)
		binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		binding.recyclerView.adapter = DebugToolAdapter(functions)
	}
	
	data class DebugFunction(
		val order: Int,
		val name: String,
		val hint: String,
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
	) : RecyclerView.Adapter<DebugToolAdapter.DebugToolViewHolder>() {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugToolViewHolder {
			val itemView = layoutInflater.inflate(R.layout.item_co_debug_tool, parent, false)
			return DebugToolViewHolder(itemView.rootView)
		}
		
		override fun getItemCount(): Int {
			return functions.size
		}
		
		override fun onBindViewHolder(holder: DebugToolViewHolder, position: Int) {
			val title = holder.findViewById<TextView>(R.id.item_title)
			val itemDesc = holder.findViewById<TextView>(R.id.item_desc)
			val itemHint = holder.findViewById<TextView>(R.id.item_hint)
			val function = functions[position]
			title.text = function.name
			if (function.desc.isNotEmpty()) {
				itemDesc.text = function.desc
				itemDesc.visibility = View.VISIBLE
			} else {
				itemDesc.visibility = View.GONE
			}
			if (function.hint.isNotEmpty()) {
				itemHint.text = function.hint
				itemHint.visibility = View.VISIBLE
			} else {
				itemHint.visibility = View.GONE
			}
			if (function.enable) {
				holder.itemView.setOnClickListener {
					function.invoke()
					this@CoDebugToolDialogFragment.dialog?.cancel()
				}
			}
		}
		
		inner class DebugToolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
			
			private val views = SparseArray<View>()
			
			@Suppress("UNCHECKED_CAST")
			fun <T : View> findViewById(@IdRes id: Int): T {
				var view = views[id]
				if (view == null) {
					view = itemView.findViewById(id)
					views[id] = view
				}
				return view as T
			}
		}
	}
}