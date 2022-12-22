package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
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
import java.util.concurrent.Executors

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
	
	private val executor = Executors.newCachedThreadPool()
	private val handler = Handler(Looper.myLooper()!!)
	
	private var refreshFailedDuration = 500L
	private var loadFailedDuration = 500L
	
	protected var pageIndex = 1
	
	companion object {
		const val PREFETCH_SIZE = 5
	}
	
	/**
	 * 设置下拉刷新失败持续时间
	 */
	protected fun setRefreshFailedDuration(duration: Long) {
		this.refreshFailedDuration = duration
	}
	
	/**
	 * 设置上拉加载失败持续时间
	 */
	protected fun setLoadFailedDuration(duration: Long) {
		this.loadFailedDuration = duration
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
		emptyView.setIcon(R.string.ic_help, R.color.abs_list_empty)
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
			if (success) {
				refreshLayout.refreshFinished()
				emptyView.visibility = View.GONE
				adapter.removeAll()
				adapter.addItems(dataItems!!)
			} else {
				// 延迟0.5s，显示empty视图
				delayInvoke(refreshFailedDuration) {
					refreshLayout.refreshFinished()
					Toast.makeText(requireContext(), R.string.abs_list_refresh_failure, Toast.LENGTH_SHORT).show()
					if (adapter.isEmpty()) {
						emptyView.visibility = View.VISIBLE
					}
				}
			}
		} else {
			if (success) {
				adapter.addItems(dataItems!!)
				recyclerView.loadFinished(true)
			} else {
				// 延迟0.5s
				delayInvoke(loadFailedDuration) {
					recyclerView.loadFinished(false)
					Toast.makeText(requireContext(), R.string.abs_list_load_failure, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}
	
	/**
	 * 延迟调用
	 */
	private fun delayInvoke(duration: Long, callback: () -> Unit) {
		executor.execute {
			Thread.sleep(duration)
			handler.post {
				callback.invoke()
			}
		}
	}
	
	/**
	 * 添加HeaderView
	 */
	fun addHeaderView(view: View) {
		adapter.addHeaderView(view)
	}
	
	/**
	 * 添加多个HeaderView
	 */
	fun addHeaderViews(views: Collection<View>) {
		adapter.addHeaderViews(views)
	}
	
	/**
	 * 添加FooterView
	 */
	fun addFooterView(view: View) {
		adapter.addFooterView(view)
		adapter.refreshAllItems()
	}
	
	/**
	 * 添加多个FooterView
	 */
	fun addFooterViews(views: Collection<View>) {
		adapter.addFooterViews(views)
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
	}
	
	/**
	 * 关闭加载更多
	 */
	private fun disableLoadMore() {
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