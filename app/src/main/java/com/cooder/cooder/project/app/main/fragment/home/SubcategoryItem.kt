package com.cooder.cooder.project.app.main.fragment.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.model.Subcategory
import com.cooder.cooder.project.common.ui.view.expends.load
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/14 20:52
 *
 * 介绍：子类别Item
 */
class SubcategoryItem(
	subcategoryList: List<Subcategory>
) : CooderDataItem<List<Subcategory>, RecyclerView.ViewHolder>(subcategoryList) {
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
		val context = holder.itemView.context
		val gridView = holder.itemView as RecyclerView
		gridView.adapter = GridAdapter(context)
	}
	
	override fun getItemView(parent: ViewGroup): View? {
		val gridView = RecyclerView(parent.context)
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
		params.bottomMargin = 10.dp.toInt()
		gridView.layoutManager = GridLayoutManager(parent.context, 5)
		gridView.layoutParams = params
		gridView.setBackgroundColor(Color.WHITE)
		return gridView
	}
	
	inner class GridAdapter(
		private val context: Context
	) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
		
		private val inflater = LayoutInflater.from(context)
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
			val view = inflater.inflate(R.layout.layout_home_op_grid_item, parent, false)
			return object : RecyclerView.ViewHolder(view) {}
		}
		
		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
			val image: ImageView = holder.itemView.findViewById(R.id.item_image)
			val title: TextView = holder.itemView.findViewById(R.id.item_title)
			val subcategory = data[position]
			image.load(subcategory.subcategoryIcon)
			title.text = subcategory.subcategoryName
			holder.itemView.setOnClickListener {
				Toast.makeText(context, subcategory.subcategoryName, Toast.LENGTH_SHORT).show()
			}
		}
		
		override fun getItemCount(): Int {
			return data.size
		}
	}
}