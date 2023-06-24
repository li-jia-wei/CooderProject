package com.cooder.cooder.biz_home.ui.goods

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.cooder.cooder.biz_home.databinding.ActivityGoodsListBinding
import com.cooder.cooder.common.route.CoRoute
import com.cooder.cooder.common.route.RoutePath
import com.cooder.cooder.common.ui.component.CoBaseActivity
import com.cooder.cooder.library.util.expends.setStatusBar

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 12:25
 *
 * 介绍：GoodsListActivity
 */
@Route(path = RoutePath.BizHome.ACTIVITY_GOODS_GOODS_LIST)
class GoodsListActivity : CoBaseActivity<ActivityGoodsListBinding>() {

    @JvmField
    @Autowired
    var categoryId = ""

    @JvmField
    @Autowired
    var subcategoryId = ""

    @JvmField
    @Autowired
    var subcategoryTitle = ""

    private companion object {
        const val TAG_GOODS_LIST_FRAGMENT = "GOODS_LIST_FRAGMENT"
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityGoodsListBinding {
        return ActivityGoodsListBinding.inflate(inflater)
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        CoRoute.inject(this)

        setStatusBar(true, Color.WHITE)

        binding.actionBack.setOnClickListener {
            onBackPressed(Activity.RESULT_CANCELED)
        }

        binding.subcategoryTitle.text = subcategoryTitle

        var fragment = supportFragmentManager.findFragmentByTag(TAG_GOODS_LIST_FRAGMENT)
        if (fragment == null) {
            fragment = GoodsListFragment.newInstance(categoryId, subcategoryId)
        }
        val ft = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            ft.add(binding.container.id, fragment, TAG_GOODS_LIST_FRAGMENT)
        }
        ft.show(fragment).commitNowAllowingStateLoss()
    }
}