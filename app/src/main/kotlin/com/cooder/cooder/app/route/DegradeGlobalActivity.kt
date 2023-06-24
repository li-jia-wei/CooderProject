package com.cooder.cooder.app.route

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.app.databinding.ActivityDegradeGlobalBinding
import com.cooder.cooder.common.route.CoRoute
import com.cooder.cooder.common.route.RoutePath
import com.cooder.cooder.common.ui.component.CoBaseActivity

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/8 20:01
 *
 * 介绍：DegradeGlobalActivity
 */
@Route(path = RoutePath.App.ACTIVITY_ROUTE_DEGRADE_GLOBAL)
class DegradeGlobalActivity : CoBaseActivity<ActivityDegradeGlobalBinding>() {

    @JvmField
    @Autowired
    var degradeTitle = ""

    @JvmField
    @Autowired
    var degradeDesc = ""

    @JvmField
    @Autowired
    var degradeAction: String? = null

    override fun getViewBinding(inflater: LayoutInflater): ActivityDegradeGlobalBinding {
        return ActivityDegradeGlobalBinding.inflate(inflater)
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        CoRoute.inject(this)

        binding.emptyView.setTitle(degradeTitle)
        binding.emptyView.setDesc(degradeDesc)

        degradeAction?.apply {
            binding.emptyView.setOnClickHelpActionListener {
                CoRoute.startActivityForBrowser(this)
            }
        }

        // 设置刷新点击事件
        binding.emptyView.setOnClickRefreshListener {
            showToast("刷新")
        }

        binding.actionBack.setOnClickListener {
            onBackPressed(Activity.RESULT_CANCELED)
        }
    }
}