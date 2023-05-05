package com.cooder.cooder.project.app.biz.detail

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.library.util.expends.hintNavigationBar
import com.cooder.cooder.library.util.expends.immersiveStatusBar
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.ActivityDetailBinding
import com.cooder.cooder.project.app.model.DetailModel
import com.cooder.cooder.project.app.model.GoodsModel
import com.cooder.cooder.project.app.model.selectPrice
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.cooder.project.common.ui.view.EmptyView
import com.cooder.cooder.ui.item.CoAdapter
import com.cooder.cooder.ui.item.CoDataItem

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/29 21:13
 *
 * 介绍：DetailActivity
 */
@Route(path = RoutePath.ACTIVITY_BIZ_DETAIL_DETAIL)
class DetailActivity : CoBaseActivity<ActivityDetailBinding>() {
	
	@JvmField
	@Autowired
	var goodsId: String? = null
	
	@JvmField
	@Autowired
	var goodsModel: GoodsModel? = null
	
	private val viewModel by lazy { DetailViewModel.get(goodsId!!, this) }
	
	private var emptyView: EmptyView? = null
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityDetailBinding {
		return ActivityDetailBinding.inflate(inflater)
	}
	
	override fun onCreateActivity(savedInstanceState: Bundle?) {
		CoRoute.inject(this)
		immersiveStatusBar(true)
		
		hintNavigationBar()
		
		assert(!TextUtils.isEmpty(goodsId)) { "goodsId must be not null or blank!" }
		
		initView()
		
		preBindData()
		
		viewModel.detailLiveData.observe(this) {
			if (it.isSuccessful()) {
				bindData(it.data!!)
			} else {
				showEmptyView()
			}
		}
		viewModel.queryDetail()
	}
	
	private fun initView() {
		binding.actionBack.setOnClickListener {
			onBackPressed(Activity.RESULT_CANCELED)
		}
		binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		binding.recyclerView.adapter = adapter
		adapter.clearAnimation()
	}
	
	private fun showEmptyView() {
		if (emptyView == null) {
			emptyView = EmptyView(this)
			emptyView!!.apply {
				this.setIcon(R.string.categoru_empty_icon)
				this.setDesc(R.string.category_empty_desc)
				this.setRefreshButton(R.string.category_empty_refresh)
				this.setOnClickRefreshListener { viewModel.queryDetail() }
				this.setBackgroundColor(Color.WHITE)
				this.layoutParams = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
			}
			binding.root.addView(emptyView!!)
		}
		binding.recyclerView.visibility = View.GONE
		binding.bottomLayout.visibility = View.GONE
		emptyView!!.visibility = View.VISIBLE
	}
	
	/**
	 * 预渲染
	 */
	private fun preBindData() {
		if (goodsModel == null) return
		val adapter = binding.recyclerView.adapter as CoAdapter
		if (adapter.itemCount == 0) {
			val headerItem = HeaderItem(
				goodsModel!!.sliderImages,
				selectPrice(goodsModel!!.groupPrice, goodsModel!!.marketPrice),
				goodsModel!!.completedNumText,
				goodsModel!!.goodsName
			)
			adapter.addItem(headerItem, false)
		}
	}
	
	private fun bindData(model: DetailModel) {
		binding.recyclerView.visibility = View.VISIBLE
		binding.bottomLayout.visibility = View.VISIBLE
		emptyView?.visibility = View.GONE
		
		val adapter = binding.recyclerView.adapter as CoAdapter
		val dataItems = mutableListOf<CoDataItem<*, *>>()
		
		// 头部Item
		dataItems += HeaderItem(
			model.sliderImages,
			selectPrice(model.groupPrice, model.marketPrice),
			model.completedNumText,
			model.goodsName
		)
		
		// 评论Item
		dataItems += CommentItem(model)
		
		// 店铺Item
		dataItems += ShopItem(model)
		
		// 商品描述Item
		dataItems += GoodsAttrItem(model)
		
		// 图库Item
		
		// 相似商品
		
		adapter.removeAllItems()
		adapter.addItems(dataItems, true)
	}
}