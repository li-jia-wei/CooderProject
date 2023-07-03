package com.cooder.project.biz_home.fragment.category

import android.util.SparseIntArray
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.cooder.project.biz_home.model.SubcategoryMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/18 21:18
 *
 * 介绍：CategorySpanSizeLookUp
 */
class CategorySpanSizeLookUp(
	private val spanSize: Int,
	private val subcategoryListCallback: () -> List<SubcategoryMo>?
) : SpanSizeLookup() {
	
	private val groupSpanSizeOffset = SparseIntArray()
	
	override fun getSpanSize(position: Int): Int {
		val subcategoryList = subcategoryListCallback.invoke() ?: return 1
		if (position == subcategoryList.size - 1) return 1
		
		val groupName = subcategoryList[position].groupName
		val nextGroupName = subcategoryList[position + 1].groupName
		if (groupName == nextGroupName) return 1
		val size = groupSpanSizeOffset.size()
		val indexOfKey = groupSpanSizeOffset.indexOfKey(position)
		val lastGroupOffset = if (size == 0 || indexOfKey == 0) {
			0
		} else if (indexOfKey > 0) {
			groupSpanSizeOffset.valueAt(indexOfKey - 1)
		} else {
			groupSpanSizeOffset.valueAt(size - 1)
		}
		val spanSize = spanSize - (position + lastGroupOffset) % spanSize
		if (indexOfKey < 0) {
			val groupOffset = lastGroupOffset + spanSize - 1
			groupSpanSizeOffset.put(position, groupOffset)
		}
		return spanSize
	}
	
	fun clear() {
		this.groupSpanSizeOffset.clear()
	}
}