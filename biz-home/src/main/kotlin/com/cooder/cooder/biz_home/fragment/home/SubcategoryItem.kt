package com.cooder.cooder.biz_home.fragment.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.biz_home.databinding.ItemHomeOpGridBinding
import com.cooder.cooder.common.route.CoRoute
import com.cooder.cooder.common.route.RoutePath
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.pub_mod.model.Subcategory
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
        val params = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = 10.dpInt
        gridView.layoutManager = GridLayoutManager(parent.context, 5)
        gridView.layoutParams = params
        gridView.setBackgroundColor(Color.WHITE)
        return gridView
    }

    inner class GridAdapter(
        private val context: Context
    ) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

        private val inflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
            val binding = ItemHomeOpGridBinding.inflate(inflater, parent, false)
            return GridViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
            val subcategory = subcategoryList[position]
            holder.binding.subCategory = subcategory
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("categoryId", subcategory.categoryId)
                bundle.putString("subcategoryId", subcategory.subcategoryId)
                bundle.putString("subcategoryTitle", subcategory.subcategoryName)
                CoRoute.startActivity(RoutePath.BizHome.ACTIVITY_GOODS_GOODS_LIST, bundle)
            }
        }

        override fun getItemCount(): Int {
            return subcategoryList.size
        }

        inner class GridViewHolder(val binding: ItemHomeOpGridBinding) :
            CoViewHolder(binding.root) {

        }
    }
}