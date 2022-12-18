package com.cooder.cooder.project.app.main.route

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.common.ui.component.CooderBaseActivity
import com.cooder.cooder.project.common.ui.view.EmptyView
import com.cooder.cooder.project.common.ui.view.IconFontTextView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 20:01
 *
 * 介绍：DegradeGlobalActivity
 */
@Route(path = RoutePath.ACTIVITY_DEGRADE_GLOBAL)
class DegradeGlobalActivity : CooderBaseActivity() {
	
	companion object {
		const val DEGRADE_TITLE = "degradeTitle"
		const val DEGRADE_DESC = "degradeDesc"
		const val DEGRADE_ACTION = "degradeAction"
	}
	
	@JvmField
	@Autowired
	var degradeTitle: String? = null
	
	@JvmField
	@Autowired
	var degradeDesc: String? = null
	
	@JvmField
	@Autowired
	var degradeAction: String? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.layout_degrade_global)
		
		// 给@Autowired注解的属性赋值
		ARouter.getInstance().inject(this)
		
		val actionBack: IconFontTextView = findViewById(R.id.action_back)
		val emptyView: EmptyView = findViewById(R.id.empty_view)
		degradeTitle?.let { emptyView.setTitle(it) }
		degradeDesc?.let { emptyView.setDesc(it) }
		// 设置帮助页面
		if (degradeAction != null) {
			emptyView.setOnClickHelpActionListener {
				CooderRoute.startActivityForBrowser(degradeAction)
			}
		}
		// 设置刷新点击事件
		emptyView.setOnRefreshListener() {
		
		}
		
		actionBack.setOnClickListener {
			finishWithResultCanceled()
		}
	}
}