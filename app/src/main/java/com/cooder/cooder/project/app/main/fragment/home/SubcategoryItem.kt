package com.cooder.cooder.project.app.main.fragment.home

import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.project.app.main.model.Subcategory
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:52
 *
 * 介绍：GridItem
 */
class SubcategoryItem(
	subcategoryList: List<Subcategory>
) : CooderDataItem<List<Subcategory>, RecyclerView.ViewHolder>(subcategoryList) {
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
	
	}
}