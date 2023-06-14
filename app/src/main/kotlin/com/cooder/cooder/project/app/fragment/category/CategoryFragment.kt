package com.cooder.cooder.project.app.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.MainActivity
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.FragmentCategoryBinding
import com.cooder.cooder.project.app.model.Subcategory
import com.cooder.cooder.project.app.model.TabCategory
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.app.route.RoutePath
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
class CategoryFragment : CoBaseFragment<FragmentCategoryBinding>() {
	
	private val viewModel by lazy { ViewModelProvider(this)[CategoryViewModel::class.java] }
	
	private var emptyView: EmptyView? = null
	
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
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCategoryBinding {
		return FragmentCategoryBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		queryCategoryList()
	}
	
	/**
	 * 查询类别
	 */
	private fun queryCategoryList() {
		viewModel.queryTabCategoryList().observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				onQueryCategoryListSuccess(it.data!!)
			} else {
				showToast(it.msg)
				showEmptyView()
			}
		}
	}
	
	private fun onQueryCategoryListSuccess(data: List<TabCategory>) {
		if (isNotAlive()) return
		emptyView?.visibility = View.GONE
		binding.sliderView.visibility = View.VISIBLE
		binding.sliderView.bindMenuView(itemCount = data.size, callback = object : CoSliderView.BindCallback {
			override fun onBindView(holder: CoViewHolder, position: Int) {
				val category = data[position]
				val title = holder.findViewById<TextView>(R2.id.menu_title)
				title.text = category.categoryName
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
		CoTabBottomLayout.clipBottomPadding(binding.sliderView.menuView, height)
	}
	
	/**
	 * 查询子类别
	 */
	private fun querySubcategoryList(categoryId: String) {
		viewModel.querySubcategoryList(categoryId).observe(viewLifecycleOwner) {
			if (it.result.isSuccessful()) {
				subcategoryListCache[it.categoryId] = it.result.data!!
				onQuerySubcategoryListSuccess(it.result.data!!, it.categoryId)
			} else {
				showToast(it.result.msg)
			}
		}
	}
	
	/**
	 * 当子类别查询成功时
	 */
	private fun onQuerySubcategoryListSuccess(data: List<Subcategory>, categoryId: String) {
		this.currentCategoryId = categoryId
		spanSizeLookUp.clear()
		if (layoutManager.spanSizeLookup != spanSizeLookUp) {
			layoutManager.spanSizeLookup = spanSizeLookUp
		}
		binding.sliderView.bindContentView(itemCount = data.size, itemDecoration = itemDecoration, layoutManager = layoutManager, callback = object : CoSliderView.BindCallback {
			override fun onBindView(holder: CoViewHolder, position: Int) {
				val subcategory = data[position]
				val image = holder.findViewById<ImageView>(R2.id.content_image)
				val title = holder.findViewById<TextView>(R2.id.content_title)
				image.load(subcategory.subcategoryIcon)
				title.text = subcategory.subcategoryName
			}
			
			override fun onItemClick(holder: CoViewHolder, position: Int) {
				if (isNotAlive()) return
				val subcategory = data[position]
				val bundle = Bundle()
				bundle.putString("categoryId", subcategory.categoryId)
				bundle.putString("subcategoryId", subcategory.subcategoryId)
				bundle.putString("subcategoryTitle", subcategory.subcategoryName)
				CoRoute.startActivity(RoutePath.ACTIVITY_BIZ_GOODS_GOODS_LIST, bundle)
			}
		})
		val height = (requireActivity() as MainActivity).getTabBottomLayoutHeight().dpInt
		CoTabBottomLayout.clipBottomPadding(binding.sliderView.contentView, height)
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
			binding.root.addView(emptyView!!)
		}
		binding.sliderView.visibility = View.GONE
		emptyView?.visibility = View.VISIBLE
	}
}