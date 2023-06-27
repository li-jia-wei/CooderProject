package com.cooder.project.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cooder.library.library.log.CoLog
import com.cooder.library.ui.item.CoAdapter
import com.cooder.project.common.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/4 06:35
 *
 * 介绍：CoRecyclerView
 */
class CoRecyclerView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
	
	private var isLoadingMore = false
	private var footerView: View? = null
	private var loadMoreScrollListener: OnScrollListener? = null
	
	private companion object {
		/**
		 * 检查向下滑动
		 */
		private const val CHECK_SCROLLING_DOWN = 1
		
		/**
		 * 检查向上滑动
		 */
		private const val CHECK_SCROLLING_UP = -1
	}
	
	/**
	 * 向下滑动，加载更多监听
	 */
	inner class LoadMoreScrollListener(
		private val prefetchSize: Int,
		private val callback: () -> Unit
	) : OnScrollListener() {
		
		private val coAdapter = adapter as CoAdapter
		
		override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
			// 根据当前的华东状态，决定要不要添加FooterView，要不要执行上拉加载分页动作
			if (isLoadingMore) {
				return
			}
			
			val totalItemCount = coAdapter.itemCount
			if (totalItemCount <= 0) {
				return
			}
			
			// 判断是否能够继续向下滑动
			val canScrollVertical = recyclerView.canScrollVertically(CHECK_SCROLLING_DOWN)
			
			// 特殊情况，咱们的列表依旧滑动到底部，但是分页失败了
			val lastVisibleItem = findLastVisibleItemPosition(recyclerView)
			val firstVisibleItem = findFirstVisibleItem(recyclerView)
			if (lastVisibleItem <= 0) {
				return
			}
			
			// 到达底部
			val arriveBottom = lastVisibleItem >= totalItemCount - 1 && firstVisibleItem > 0
			// 可以向下滑动，或者当前已经滑动到最底下，此时拖动列表可以分页的
			if (newState == SCROLL_STATE_DRAGGING && (canScrollVertical || arriveBottom)) {
				addFooterView()
			}
			
			// 未滚动状态下停止
			if (newState != SCROLL_STATE_IDLE) {
				return
			}
			
			// 预加载，不需要等待，滑动到最后一个Item的时候就发出下一页的加载动作
			val arriveRefreshPosition = totalItemCount - lastVisibleItem <= prefetchSize
			if (!arriveRefreshPosition) {
				return
			}
			isLoadingMore = true
			callback.invoke()
		}
		
		/**
		 * 添加FooterView
		 */
		private fun addFooterView() {
			val footerView = getFooterView()
			// 在边界状态下，多次添加的情况或者add之前先remove不会的到立即刷新
			// 避免在addFooterView时还没有从RecyclerView上remove掉，导致出现Add view must call remove view from it parent first exception! 的异常
			if (footerView.parent != null) {
				footerView.post {
					coAdapter.addFooterView(footerView)
				}
			} else {
				coAdapter.addFooterView(footerView)
			}
		}
		
		/**
		 * 获取FooterView
		 */
		private fun getFooterView(): View {
			if (footerView == null) {
				footerView = LayoutInflater.from(context).inflate(R.layout.layout_footer_loading, this@CoRecyclerView, false)
				val params = footerView!!.layoutParams as LayoutParams
				footerView!!.layoutParams = params
			}
			return footerView!!
		}
		
		/**
		 * 查询最后一个可见Item的位置
		 */
		private fun findLastVisibleItemPosition(recyclerView: RecyclerView): Int {
			when (val manager = recyclerView.layoutManager) {
				is LinearLayoutManager -> {
					return manager.findLastVisibleItemPosition()
				}
				
				is StaggeredGridLayoutManager -> {
					return manager.findLastVisibleItemPositions(null)[0]
				}
			}
			return -1
		}
		
		/**
		 * 查询第一个可见Item的位置
		 */
		private fun findFirstVisibleItem(recyclerView: RecyclerView): Int {
			when (val manager = recyclerView.layoutManager) {
				is LinearLayoutManager -> {
					return manager.findFirstVisibleItemPosition()
				}
				
				is StaggeredGridLayoutManager -> {
					return manager.findFirstVisibleItemPositions(null)[0]
				}
			}
			return -1
		}
	}
	
	/**
	 * 开启加载更多功能
	 */
	fun enableLoadMore(prefetchSize: Int, callback: () -> Unit) {
		if (adapter !is CoAdapter) {
			CoLog.e("enableLoadMore must use CoAdapter!")
			return
		}
		loadMoreScrollListener?.let {
			removeOnScrollListener(it)
		}
		loadMoreScrollListener = LoadMoreScrollListener(prefetchSize, callback)
		addOnScrollListener(loadMoreScrollListener!!)
	}
	
	/**
	 * 关闭加载更多功能
	 */
	fun disableLoadMore() {
		if (adapter !is CoAdapter) {
			CoLog.e("disableLoadMore must use CoAdapter!")
			return
		}
		val coAdapter = adapter as CoAdapter
		footerView?.let {
			if (footerView!!.parent != null) {
				coAdapter.removeFooterView(footerView!!)
			}
		}
		loadMoreScrollListener?.let {
			removeOnScrollListener(it)
			loadMoreScrollListener = null
			footerView = null
			isLoadingMore = false
		}
	}
	
	/**
	 * 是否正在加载
	 */
	fun isLoading(): Boolean {
		return isLoadingMore
	}
	
	/**
	 * 加载完成
	 */
	fun loadFinished(success: Boolean) {
		if (adapter !is CoAdapter) {
			CoLog.e("loadFinished must use CoAdapter!")
			return
		}
		isLoadingMore = false
		val coAdapter = adapter as CoAdapter
		if (!success) {
			footerView?.let {
				if (footerView!!.parent != null) {
					coAdapter.removeFooterView(footerView!!)
				}
			}
		}
	}
}