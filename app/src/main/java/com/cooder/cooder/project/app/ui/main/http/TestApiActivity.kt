package com.cooder.cooder.project.app.ui.main.http

import android.os.Bundle
import android.widget.Button
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.TestApi
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 18:24
 *
 * 介绍：TestApiActivity
 */
class TestApiActivity : CooderBaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test_api)
		
		val makeNetworkRequest: Button = findViewById(R.id.make_network_request)
		makeNetworkRequest.setOnClickListener {
			Thread {
				ApiFactory.create(TestApi::class.java).listCities("LJW").execute()
			}.start()
		}
	}
}