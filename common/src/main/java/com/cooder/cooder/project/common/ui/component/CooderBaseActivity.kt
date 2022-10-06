package com.cooder.cooder.project.common.ui.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 项目名称：CooderProject
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/10/3 14:16
 *
 * 文件介绍：CooderBaseActivity
 */
open class CooderBaseActivity : AppCompatActivity(), CooderBaseActionInterface {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
}