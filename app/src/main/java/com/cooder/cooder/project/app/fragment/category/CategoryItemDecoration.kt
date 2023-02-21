package com.cooder.cooder.project.app.fragment.category

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.expends.dp
import com.cooder.cooder.library.util.expends.dpInt

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/18 16:35
 *
 * 介绍：CategoryItem装饰器
 */
class CategoryItemDecoration(
	private val spanCount: Int,
	private val groupNameCallback: (position: Int) -> String
) : RecyclerView.ItemDecoration() {
	
	private val groupFirstPositions = mutableMapOf<String, Int>()
	
	private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	
	init {
		paint.color = Color.BLACK
		paint.textSize = 15.dp
		paint.style = Paint.Style.FILL
	}
	
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		// 1.根据View找到处于RecyclerView中的position
		val adapterPosition = parent.getChildAdapterPosition(view)
		if (parent.adapter == null || adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) return
		
		// 2.拿到当前position对应的groupName
		val groupName = groupNameCallback.invoke(adapterPosition)
		
		// 3.拿到前面一个位置的groupName
		val preGroupName = if (adapterPosition > 0) groupNameCallback.invoke(adapterPosition - 1) else null
		
		val sameGroup = groupName == preGroupName
		// 4.如果当前组和上一个组不相同说明是当前组的第一个位置
		if (!sameGroup && !groupFirstPositions.containsKey(groupName)) {
			groupFirstPositions[groupName] = adapterPosition
		}
		val firstPowPosition = groupFirstPositions[groupName] ?: 0
		val sameRow = adapterPosition - firstPowPosition in 0 until spanCount
		if (!sameGroup || sameRow) {
			outRect.set(0, 40.dpInt, 0, 0)
		} else {
			outRect.set(0, 0, 0, 0)
		}
	}
	
	override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		parent.forEach {
			val adapterPosition = parent.getChildAdapterPosition(it)
			if (parent.adapter == null || adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) {
				return@forEach
			}
			val groupName = groupNameCallback.invoke(adapterPosition)
			val groupFirstPosition = groupFirstPositions[groupName]
			if (adapterPosition == groupFirstPosition) {
				val decorationBounds = Rect()
				parent.getDecoratedBoundsWithMargins(it, decorationBounds)
				val textBounds = Rect()
				paint.getTextBounds(groupName, 0, groupName.length, textBounds)
				c.drawText(groupName, 16.dp, (decorationBounds.top + 2 * textBounds.height()).toFloat(), paint)
			}
		}
	}
	
	fun clear() {
		this.groupFirstPositions.clear()
	}
}