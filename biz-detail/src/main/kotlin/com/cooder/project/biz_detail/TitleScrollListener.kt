package com.cooder.project.biz_detail

import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.util.expends.dp
import kotlin.math.abs

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/20 23:39
 *
 * 介绍：TitleScrollListener
 */
class TitleScrollListener @JvmOverloads constructor(
	thresholdDp: Float = 290F,
	private val alphaCallback: (alpha: Float) -> Unit
) : RecyclerView.OnScrollListener() {
	
	private var thresholdPx = thresholdDp.dp
	
	private var lastAlpha = 0F
	
	private companion object {
		private const val FINISH_ALPHA = 0.95F
	}
	
	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)
		
		val viewHolder = recyclerView.findViewHolderForAdapterPosition(0) ?: return
		val top = abs(viewHolder.itemView.top).toFloat()
		val alpha = (top / thresholdPx) * FINISH_ALPHA
		if (alpha < FINISH_ALPHA) {
			this.alphaCallback.invoke(alpha)
			this.lastAlpha = alpha
		} else {
			if (this.lastAlpha != FINISH_ALPHA) {
				this.lastAlpha = FINISH_ALPHA
				this.alphaCallback.invoke(this.lastAlpha)
			}
		}
	}
}