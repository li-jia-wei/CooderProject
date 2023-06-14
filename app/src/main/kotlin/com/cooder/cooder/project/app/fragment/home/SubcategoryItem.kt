package com.cooder.cooder.project.app.fragment.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.ItemHomeOpGridBinding
import com.cooder.cooder.project.app.model.Subcategory
import com.cooder.cooder.project.common.ui.view.expends.load
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder

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
	private val subcategoryList: List<Subcategory>
) : CoDataItem<List<Subcategory>, CoViewHolder>() {
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val context = holder.itemView.context
		val gridView = holder.itemView as RecyclerView
		gridView.adapter = GridAdapter(context)
	}
	
	override fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View {
		val gridView = RecyclerView(parent.context)
		val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
		params.bottomMargin = 10.dpInt
		gridView.layoutManager = GridLayoutManager(parent.context, 5)
		gridView.layoutParams = params
		gridView.setBackgroundColor(Color.WHITE)
		return gridView
	}
	
	inner class GridAdapter(
		private val context: Context
	) : RecyclerView.Adapter<CoViewHolder>() {
		
		private val inflater = LayoutInflater.from(context)
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoViewHolder {
			val view = inflater.inflate(R.layout.item_home_op_grid, parent, false)
			return CoViewHolder(view)
		}
		
		override fun onBindViewHolder(holder: CoViewHolder, position: Int) {
			val binding = ItemHomeOpGridBinding.bind(holder.itemView)
//			val image: ImageView = holder.findViewById(R.id.item_image)
//			val title: TextView = holder.findViewById(R.id.item_title)
			val subcategory = subcategoryList[position]
			binding.itemImage.load(subcategory.subcategoryIcon)
			binding.itemTitle.text = subcategory.subcategoryName
			holder.itemView.setOnClickListener {
				Toast.makeText(context, subcategory.subcategoryName, Toast.LENGTH_SHORT).show()
			}
		}
		
		override fun getItemCount(): Int {
			return subcategoryList.size
		}
	}
}