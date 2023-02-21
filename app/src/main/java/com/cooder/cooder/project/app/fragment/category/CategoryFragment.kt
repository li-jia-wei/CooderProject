package com.cooder.cooder.project.app.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.cooder.library.log.CoLog
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.MainActivity
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.CategoryApi
import com.cooder.cooder.project.app.model.Subcategory
import com.cooder.cooder.project.app.model.TabCategory
import com.cooder.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.cooder.project.common.ui.view.EmptyView
import com.cooder.cooder.project.common.ui.view.expends.load
import com.cooder.cooder.ui.item.CoViewHolder
import com.cooder.cooder.ui.slider.CoSliderView
import com.cooder.cooder.ui.tab.bottom.CoTabBottomLayout
import com.cooder.cooder.ui.R as R2

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:47
 *
 * 介绍：类别Fragment
 */
class CategoryFragment : CoBaseFragment() {
	
	private var emptyView: EmptyView? = null
	private lateinit var sliderView: CoSliderView
	private val layoutManager = GridLayoutManager(context, SPAN_COUNT)
	
	private val subcategoryListCache = mutableMapOf<String, List<Subcategory>>()
	private var currentCategoryId = ""
	
	private companion object {
		private const val SPAN_COUNT = 3
	}
	
	private val spanSizeLookUp = CategorySpanSizeLookUp(SPAN_COUNT) {
		subcategoryListCache[currentCategoryId]
	}
	
	private val itemDecoration = CategoryItemDecoration(SPAN_COUNT) {
		subcategoryListCache[currentCategoryId]!![it].groupName
	}
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_category
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		sliderView = findViewById(R.id.slider_view)
		queryCategoryList()
	}
	
	private fun queryCategoryList() {
		ApiFactory.create(CategoryApi::class.java).queryCategoryList().enqueue(object : CoCallback<List<TabCategory>> {
			override fun onSuccess(response: CoResponse<List<TabCategory>>) {
				if (response.isSuccessful() && response.data != null) {
					onQueryCategoryListSuccess(response.data!!)
				} else {
					showEmptyView()
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				showEmptyView()
			}
		})
	}
	
	private fun onQueryCategoryListSuccess(data: List<TabCategory>) {
		if (isNotAlive()) return
		emptyView?.visibility = View.GONE
		sliderView.visibility = View.VISIBLE
		sliderView.bindMenuView(itemCount = data.size, callback = object : CoSliderView.BindCallback {
			override fun onBindView(holder: CoViewHolder, position: Int) {
				val category = data[position]
				val title = holder.findViewById<TextView>(R2.id.menu_title)
				title?.text = category.categoryName
			}
			
			override fun onItemClick(holder: CoViewHolder, position: Int) {
				val categoryId = data[position].categoryId
				if (subcategoryListCache.containsKey(categoryId)) {
					onQuerySubcategoryListSuccess(subcategoryListCache[categoryId]!!, categoryId)
				} else {
					querySubcategoryList(categoryId)
				}
			}
		})
		val height = (requireActivity() as MainActivity).getTabBottomLayoutHeight().dpInt
		CoTabBottomLayout.clipBottomPadding(sliderView.menuView, height)
	}
	
	private fun querySubcategoryList(categoryId: String) {
		ApiFactory.create(CategoryApi::class.java).querySubcategoryList(categoryId).enqueue(object : CoCallback<List<Subcategory>> {
			override fun onSuccess(response: CoResponse<List<Subcategory>>) {
				if (response.isSuccessful() && response.data != null) {
					subcategoryListCache[categoryId] = response.data!!
					onQuerySubcategoryListSuccess(response.data!!, categoryId)
				} else {
					CoLog.i("What")
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				CoLog.e(throwable.message)
			}
		})
	}
	
	private fun onQuerySubcategoryListSuccess(data: List<Subcategory>, categoryId: String) {
		this.currentCategoryId =  categoryId
		spanSizeLookUp.clear()
		if (layoutManager.spanSizeLookup != spanSizeLookUp) {
			layoutManager.spanSizeLookup = spanSizeLookUp
		}
		sliderView.bindContentView(itemCount = data.size, itemDecoration = itemDecoration, layoutManager = layoutManager, callback = object : CoSliderView.BindCallback {
			override fun onBindView(holder: CoViewHolder, position: Int) {
				val subcategory = data[position]
				val image = holder.findViewById<ImageView>(R2.id.content_image)
				val title = holder.findViewById<TextView>(R2.id.content_title)
				image.load(subcategory.subcategoryIcon)
				title.text = subcategory.subcategoryName
			}
			
			override fun onItemClick(holder: CoViewHolder, position: Int) {
				// 跳转到类目的商品列表页
				val subcategory = data[position]
				Toast.makeText(requireContext(), "${subcategory.groupName} : ${subcategory.subcategoryName}", Toast.LENGTH_SHORT).show()
			}
		})
		val height = (requireActivity() as MainActivity).getTabBottomLayoutHeight().dpInt
		CoTabBottomLayout.clipBottomPadding(sliderView.contentView, height)
	}
	
	private fun showEmptyView() {
		if (isNotAlive()) return
		if (emptyView == null) {
			emptyView = EmptyView(requireContext())
			emptyView!!.apply {
				this.setIcon(R.string.categoru_empty_icon)
				this.setDesc(R.string.category_empty_desc)
				this.setRefreshButton(R.string.category_empty_refresh)
				this.setOnClickRefreshListener { queryCategoryList() }
				this.setBackgroundColor(Color.WHITE)
				this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			}
			addView(emptyView!!)
		}
		sliderView.visibility = View.GONE
		emptyView?.visibility = View.VISIBLE
	}
}