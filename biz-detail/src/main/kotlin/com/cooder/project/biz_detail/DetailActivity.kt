package com.cooder.project.biz_detail

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.library.library.util.expends.hintNavigationBar
import com.cooder.library.library.util.expends.immersiveStatusBar
import com.cooder.library.ui.item.CoAdapter
import com.cooder.library.ui.item.CoDataItem
import com.cooder.project.biz_detail.databinding.ActivityDetailBinding
import com.cooder.project.biz_detail.items.AttrItem
import com.cooder.project.biz_detail.items.CommentItem
import com.cooder.project.biz_detail.items.GalleryItem
import com.cooder.project.biz_detail.items.HeaderItem
import com.cooder.project.biz_detail.items.ShopItem
import com.cooder.project.biz_detail.items.SimilarTitleItem
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.route.RoutePath
import com.cooder.project.common.ui.component.CoBaseActivity
import com.cooder.project.common.ui.view.EmptyView
import com.cooder.project.pub_mod.items.GoodsItem
import com.cooder.project.pub_mod.model.DetailModel
import com.cooder.project.pub_mod.model.GoodsModel
import com.cooder.project.pub_mod.model.selectPrice
import com.cooder.project.service_login.LoginServiceProvider
import com.cooder.library.ui.R as RUi

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/29 21:13
 *
 * 介绍：商品详情页
 */
@Route(path = RoutePath.BizDetail.ACTIVITY_DETAIL)
class DetailActivity : CoBaseActivity<ActivityDetailBinding>() {
	
	@JvmField
	@Autowired
	var goodsId: String? = null
	
	@JvmField
	@Autowired
	var goodsModel: GoodsModel? = null
	
	private companion object {
		private const val SIMILAR_GOODS_ITEM_SPAN = 2
	}
	
	private val viewModel by lazy { DetailViewModel.get(goodsId!!, this) }
	
	private var emptyView: EmptyView? = null
	
	override fun getViewBinding(inflater: LayoutInflater): ActivityDetailBinding {
		return ActivityDetailBinding.inflate(inflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		CoRoute.inject(this)
		immersiveStatusBar(true)
		
		hintNavigationBar()
		
		assert(!TextUtils.isEmpty(goodsId)) { "goodsId must be not null or blank!" }
		
		initView()
		
		preBindData()
		
		queryDetail()
	}
	
	private fun queryDetail() {
		viewModel.queryDetail().observe(this) {
			if (it.isSuccessful()) {
				bindData(it.data!!)
			} else {
				showEmptyView()
			}
		}
	}
	
	private fun initView() {
		binding.navigationBar.setNavigationListener {
			onBackPressed(Activity.RESULT_CANCELED)
		}
		val share = binding.navigationBar.addRightIconButton(RUi.string.ic_share)
		share.setOnClickListener {
			Toast.makeText(this, "分享商品功能暂未开发...", Toast.LENGTH_SHORT).show()
		}
		binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		binding.recyclerView.adapter = adapter
		adapter.clearAnimation()
		
		binding.recyclerView.addOnScrollListener(TitleScrollListener {
			if (it == 0F) {
				binding.navigationBar.visibility = View.GONE
			} else {
				binding.navigationBar.visibility = View.VISIBLE
				binding.navigationBar.alpha = it
			}
		})
	}
	
	private fun showEmptyView() {
		if (emptyView == null) {
			emptyView = EmptyView(this)
			emptyView!!.apply {
				this.setIcon(R.string.categoru_empty_icon)
				this.setDesc(R.string.category_empty_desc)
				this.setRefreshButton(R.string.category_empty_refresh)
				this.setOnClickRefreshListener { queryDetail() }
				this.setBackgroundColor(Color.WHITE)
				this.layoutParams = ConstraintLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT
				)
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
	
	private fun bindData(detailModel: DetailModel) {
		binding.recyclerView.visibility = View.VISIBLE
		binding.bottomLayout.visibility = View.VISIBLE
		emptyView?.visibility = View.GONE
		
		val adapter = binding.recyclerView.adapter as CoAdapter
		
		val dataItems = mutableListOf<CoDataItem<*, *>>()
		
		// 头部Item
		dataItems += HeaderItem(
			detailModel.sliderImages,
			selectPrice(detailModel.groupPrice, detailModel.marketPrice),
			detailModel.completedNumText,
			detailModel.goodsName
		)
		
		// 评论Item
		dataItems += CommentItem(detailModel)
		
		// 店铺Item
		if (!detailModel.flowGoods.isNullOrEmpty()) {
			dataItems += ShopItem(detailModel)
		}
		
		// 商品详情Item
		dataItems += AttrItem(detailModel)
		
		// 图库Item
		detailModel.sliderImages?.forEach {
			dataItems += GalleryItem(it)
		}
		
		// 相似商品
		detailModel.similarGoods?.let { models ->
			dataItems += SimilarTitleItem()
			models.forEachIndexed { index, goodsModel ->
				dataItems += GoodsItem(goodsModel, false, SIMILAR_GOODS_ITEM_SPAN, index)
			}
		}
		
		adapter.removeAllItems()
		adapter.addItems(dataItems, true)
		
		updateFavoriteActionFace(detailModel.isFavorite)
		updateOrderActionFace(detailModel)
	}
	
	/**
	 * 更新价格
	 */
	private fun updateOrderActionFace(detailModel: DetailModel) {
		binding.actionPrice.text = getString(
			R.string.detail_price,
			selectPrice(detailModel.groupPrice, detailModel.marketPrice)
		)
		// 购买的点击事件 => 20230520 => 20230625
	}
	
	/**
	 * 更新是否收藏
	 */
	private fun updateFavoriteActionFace(favorite: Boolean) {
		if (!binding.actionFavorite.hasOnClickListeners()) {
			binding.actionFavorite.setOnClickListener {
				toggleFavorite()
			}
		}
		if (favorite) {
			binding.actionFavorite.setText(R.string.detail_already_favorite)
			binding.actionFavorite.setTextColor(getColor(R.color.detail_favorite))
		} else {
			binding.actionFavorite.setText(R.string.detail_favorite)
			binding.actionFavorite.setTextColor(getColor(R.color.detail_unfavorite))
		}
	}
	
	private fun toggleFavorite() {
		if (LoginServiceProvider.isNotLogin()) {
			LoginServiceProvider.loginSuccessObserver(this) { loginSuccess ->
				if (loginSuccess) {
					toggleFavorite()
				}
			}
		} else {
			binding.actionFavorite.isClickable = false
			viewModel.toggleFavorite().observe(this) {
				if (it.isSuccessful()) {
					val success = it.data!!
					updateFavoriteActionFace(success)
					val message =
						if (success) R.string.detail_success_favorite else R.string.detail_success_cancel_favorite
					showToast(message)
				}
				binding.actionFavorite.isClickable = true
			}
		}
	}
}