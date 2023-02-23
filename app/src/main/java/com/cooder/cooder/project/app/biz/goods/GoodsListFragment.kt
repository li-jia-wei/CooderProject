package com.cooder.cooder.project.app.biz.goods

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.project.app.fragment.home.GoodsItem
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.GoodsApi
import com.cooder.cooder.project.app.model.GoodsList
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.common.ui.component.CoAbsListFragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 12:27
 *
 * 介绍：GoodsListFragment
 */
class GoodsListFragment : CoAbsListFragment() {
	
	@JvmField
	@Autowired
	var categoryId = ""
	
	@JvmField
	@Autowired
	var subcategoryId = ""
	
	companion object {
		fun newInstance(categoryId: String, subcategoryId: String): Fragment? {
			val args = Bundle()
			args.putString("categoryId", categoryId)
			args.putString("subcategoryId", subcategoryId)
			val fragment = GoodsListFragment()
			fragment.arguments = args
			return fragment
		}
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		CoRoute.inject(this)
		
		loadData()
	}
	
	override fun onRefresh() {
		super.onRefresh()
		
		loadData()
	}
	
	override fun onLoadMore() {
		super.onLoadMore()
		
		loadData()
	}
	
	private fun loadData() {
		ApiFactory.create(GoodsApi::class.java)
			.queryGoodsList(categoryId, subcategoryId, 10, pageIndex)
			.enqueue(object : CoCallback<GoodsList> {
				override fun onSuccess(response: CoResponse<GoodsList>) {
					if (response.isSuccessful() && response.data != null) {
						onQueryCategoryGoodsList(response.data!!)
					} else {
						finishRefresh(null)
					}
				}
				
				override fun onFailed(throwable: Throwable) {
					finishRefresh(null)
				}
			})
	}
	
	private fun onQueryCategoryGoodsList(data: GoodsList) {
		val dataItems = mutableListOf<GoodsItem>()
		data.list.forEach {
			dataItems += GoodsItem(it, false)
		}
		finishRefresh(dataItems)
	}
	
	override fun createLayoutManager(): RecyclerView.LayoutManager {
		return GridLayoutManager(context, 2)
	}
}