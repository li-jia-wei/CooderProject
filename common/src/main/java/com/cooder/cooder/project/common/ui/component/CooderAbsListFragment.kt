package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.project.common.R
import com.cooder.cooder.project.common.ui.view.CooderRecyclerView
import com.cooder.cooder.project.common.ui.view.EmptyView
import com.cooder.cooder.ui.item.CooderAdapter
import com.cooder.cooder.ui.item.CooderDataItem
import com.cooder.cooder.ui.refresh.CooderRefresh
import com.cooder.cooder.ui.refresh.CooderRefreshLayout
import com.cooder.cooder.ui.refresh.overview.CooderOverView
import com.cooder.cooder.ui.refresh.overview.CooderTextOverView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/5 20:45
 *
 * 介绍：CooderAbsListFragment
 */
open class CooderAbsListFragment : CooderBaseFragment(), CooderRefresh.CooderRefreshListener {
	
	protected lateinit var refreshLayout: CooderRefreshLayout
	protected lateinit var recyclerView: CooderRecyclerView
	protected lateinit var emptyView: EmptyView
	protected lateinit var loadingView: ContentLoadingProgressBar
	
	protected lateinit var adapter: CooderAdapter
	protected lateinit var refreshHeaderView: CooderTextOverView
	protected lateinit var layoutManager: RecyclerView.LayoutManager
	
	private var pageIndex = 1
	
	companion object {
		const val PREFETCH_SIZE = 5
	}
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_abs_list
	}
	
	@CallSuper
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		this.refreshLayout = view.findViewById(R.id.refresh_layout)
		this.recyclerView = view.findViewById(R.id.recycler_view)
		this.emptyView = view.findViewById(R.id.empty_view)
		this.loadingView = view.findViewById(R.id.content_loading)
		
		refreshHeaderView = CooderTextOverView(requireContext())
		refreshLayout.setRefreshOverView(refreshHeaderView)
		refreshLayout.setRefreshListener(this)
		layoutManager = createLayoutManager()
		adapter = CooderAdapter(requireContext())
		
		recyclerView.layoutManager = layoutManager
		recyclerView.adapter = adapter
		
		emptyView.visibility = View.GONE
		emptyView.setIcon(R.string.ic_help)
		emptyView.setDesc(R.string.abs_list_desc)
		emptyView.setRefreshButton(R.string.abs_list_refresh)
		emptyView.setOnRefreshListener {
			onRefresh()
		}
		
		loadingView.visibility = View.VISIBLE
		pageIndex = 1
	}
	
	/**
	 * 完成刷新
	 */
	fun finishRefresh(dataItems: List<CooderDataItem<*, out RecyclerView.ViewHolder>>?) {
		val success = dataItems != null && dataItems.isNotEmpty()
		val refresh = pageIndex == 1
		if (refresh) {
			loadingView.visibility = View.GONE
			refreshLayout.refreshFinished()
			if (success) {
				emptyView.visibility = View.GONE
				adapter.removeAllItems()
				adapter.addItems(dataItems!!)
			} else {
				// 此时就需要判断列表上有没有数据，如果没有，显示出空页面状态
				if (adapter.itemCount <= 0) {
					emptyView.visibility = View.VISIBLE
				}
			}
		} else {
			if (success) {
				adapter.addItems(dataItems!!)
			}
			recyclerView.loadFinished(success)
		}
	}
	
	/**
	 * 开启加载更多
	 */
	fun enableLoadMore(callback: () -> Unit) {
		recyclerView.enableLoadMore(PREFETCH_SIZE) {
			if (refreshHeaderView.state == CooderOverView.CooderRefreshState.STATE_REFRESH) {
				// 正处于刷新状态
				recyclerView.loadFinished(false)
				@Suppress("LABEL_NAME_CLASH")
				return@enableLoadMore
			}
			pageIndex++
			callback.invoke()
		}
		return
	}
	
	/**
	 * 关闭加载更多
	 */
	fun disableLoadMore() {
		recyclerView.disableLoadMore()
	}
	
	/**
	 * 创建LayoutManager
	 */
	open fun createLayoutManager(): RecyclerView.LayoutManager {
		return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
	}
	
	/**
	 * 刷新
	 */
	@CallSuper
	override fun onRefresh() {
		if (recyclerView.isLoading()) {
			refreshLayout.post {
				refreshLayout.refreshFinished()
			}
			return
		}
		pageIndex = 1
	}
}