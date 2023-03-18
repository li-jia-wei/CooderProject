package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.executor.CoExecutor
import com.cooder.cooder.library.util.CoMainHandler
import com.cooder.cooder.project.common.R
import com.cooder.cooder.project.common.databinding.FragmentAbsListBinding
import com.cooder.cooder.ui.item.CoAdapter
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.refresh.CoRefresh
import com.cooder.cooder.ui.refresh.overview.CoOverView
import com.cooder.cooder.ui.refresh.overview.CoTextOverView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/5 20:45
 *
 * 介绍：CoAbsListFragment
 */
abstract class CoAbsListFragment : CoBaseFragment<FragmentAbsListBinding>(), CoRefresh.CoRefreshListener {
	
	protected lateinit var adapter: CoAdapter
	
	protected var pageIndex = 1
	
	private lateinit var refreshHeaderView: CoTextOverView
	
	private lateinit var layoutManager: RecyclerView.LayoutManager
	
	private var refreshFailedDuration = -1L
	private var loadFailedDuration = -1L
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAbsListBinding {
		return FragmentAbsListBinding.inflate(inflater, container, false)
	}
	
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
	
	@CallSuper
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		refreshHeaderView = CoTextOverView(requireContext())
		binding.refreshLayout.setRefreshOverView(refreshHeaderView)
		binding.refreshLayout.setRefreshListener(this)
		layoutManager = createLayoutManager()
		adapter = CoAdapter(requireContext())
		
		binding.recyclerView.layoutManager = layoutManager
		binding.recyclerView.adapter = adapter
		
		binding.emptyView.visibility = View.GONE
		binding.emptyView.setIcon(R.string.ic_help, R.color.abs_list_empty)
		binding.emptyView.setDesc(R.string.abs_list_desc)
		binding.emptyView.setRefreshButton(R.string.abs_list_refresh)
		binding.emptyView.setOnClickRefreshListener {
			onRefresh()
		}
		
		binding.loadView.visibility = View.VISIBLE
		pageIndex = 1
		
		enableLoadMore {
			onLoadMore()
		}
	}
	
	/**
	 * 完成刷新
	 */
	fun finishRefresh(dataItems: List<CoDataItem<*, out RecyclerView.ViewHolder>>?) {
		val success = !dataItems.isNullOrEmpty()
		val refresh = pageIndex == 1
		if (refresh) {
			binding.loadView.visibility = View.GONE
			if (success) {
				binding.refreshLayout.refreshFinished()
				binding.emptyView.visibility = View.GONE
				adapter.removeAll()
				adapter.addItems(dataItems!!)
			} else {
				// 延迟0.5s，显示empty视图
				loadFailureDelayInvoke(refreshFailedDuration) {
					binding.refreshLayout.refreshFinished()
					Toast.makeText(requireContext(), R.string.abs_list_refresh_failure, Toast.LENGTH_SHORT).show()
					if (adapter.isEmpty()) {
						binding.emptyView.visibility = View.VISIBLE
					}
				}
			}
		} else {
			if (success) {
				adapter.addItems(dataItems!!)
				binding.recyclerView.loadFinished(true)
			} else {
				// 延迟0.5s
				loadFailureDelayInvoke(loadFailedDuration) {
					binding.recyclerView.loadFinished(false)
					Toast.makeText(requireContext(), R.string.abs_list_load_failure, Toast.LENGTH_SHORT).show()
				}
			}
		}
	}
	
	/**
	 * 延迟调用
	 */
	private fun loadFailureDelayInvoke(duration: Long, callback: () -> Unit) {
		if (duration <= 0L) {
			callback.invoke()
		} else {
			CoExecutor.execute {
				Thread.sleep(duration)
				CoMainHandler.post {
					callback.invoke()
				}
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
		binding.recyclerView.enableLoadMore(PREFETCH_SIZE) {
			if (refreshHeaderView.state == CoOverView.CoRefreshState.STATE_REFRESH) {
				// 正处于刷新状态
				binding.recyclerView.loadFinished(false)
				@Suppress("LABEL_NAME_CLASH") return@enableLoadMore
			}
			pageIndex++
			callback.invoke()
		}
	}
	
	/**
	 * 关闭加载更多
	 */
	private fun disableLoadMore() {
		binding.recyclerView.disableLoadMore()
	}
	
	/**
	 * 创建LayoutManager
	 */
	open fun createLayoutManager(): RecyclerView.LayoutManager {
		return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
	}
	
	open fun onLoadMore() {
	
	}
	
	/**
	 * 刷新
	 */
	@CallSuper
	override fun onRefresh() {
		if (binding.recyclerView.isLoading()) {
			binding.refreshLayout.post {
				binding.refreshLayout.refreshFinished()
			}
			return
		}
		pageIndex = 1
	}
}