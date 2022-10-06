package com.cooder.cooder.project.app.ui.main

import android.content.Context
import android.os.Bundle
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.ui.main.logic.MainActivityLogic
import com.cooder.cooder.project.app.ui.main.logic.MainActivityLogic.ActivityProvider
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity

class MainActivity : CooderBaseActivity(), ActivityProvider {
	
	private lateinit var activityLogic: MainActivityLogic
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		activityLogic = MainActivityLogic(this, savedInstanceState)
	}
	
	override fun getContext(): Context {
		return this
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		activityLogic.onSaveInstanceState(outState)
	}
}