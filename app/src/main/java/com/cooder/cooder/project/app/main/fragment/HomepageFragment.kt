package com.cooder.cooder.project.app.main.fragment

import android.os.Bundle
import android.view.View
import com.cooder.cooder.library.restful.CooderCallback
import com.cooder.cooder.library.restful.CooderResponse
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.main.http.ApiFactory
import com.cooder.cooder.project.app.main.http.api.HomeApi
import com.cooder.cooder.project.app.main.model.TabCategory
import com.cooder.cooder.project.common.ui.component.CooderBaseFragment

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 23:40
 *
 * 文件介绍：主页Fragment
 */
class HomepageFragment : CooderBaseFragment() {
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_homepage
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		queryTabList()
	}
	
	/**
	 * 查询顶部Tab
	 */
	private fun queryTabList() {
		ApiFactory.create(HomeApi::class.java).queryTab().enqueue(object : CooderCallback<List<TabCategory>> {
			override fun onSuccess(response: CooderResponse<List<TabCategory>>) {
				val data: List<TabCategory>? = response.data
				if (response.isSuccess() && data != null) {
					updateUI(data)
				} else {
					showToast(response.msg)
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				showToast(throwable.message)
			}
		})
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(data: List<TabCategory>) {
	
	}
}