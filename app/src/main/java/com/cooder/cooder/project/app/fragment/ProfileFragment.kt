package com.cooder.cooder.project.app.fragment

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.http.ApiFactory
import com.cooder.cooder.project.app.http.api.AccountApi
import com.cooder.cooder.project.app.http.api.NoticeApi
import com.cooder.cooder.project.app.http.interceptor.HttpCodeInterceptor
import com.cooder.cooder.project.app.model.CourseNotice
import com.cooder.cooder.project.app.model.Notice
import com.cooder.cooder.project.app.model.UserProfile
import com.cooder.cooder.project.app.route.CoRoute
import com.cooder.cooder.project.app.route.RoutePath
import com.cooder.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.cooder.project.common.ui.view.expends.loadCircle
import com.cooder.cooder.project.common.ui.view.expends.loadCorner
import com.cooder.cooder.ui.banner.CoBanner
import com.cooder.cooder.ui.banner.core.CoBannerMo

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:47
 *
 * 介绍：配置Fragment
 */
class ProfileFragment : CoBaseFragment() {
	
	private companion object {
		private const val REQUEST_CODE_LOGIN_PROFILE = 1001
		
		private const val BANNER_CORNER = 10
	}
	
	private lateinit var userAvatar: ImageView
	private lateinit var username: TextView
	private lateinit var loginDesc: TextView
	
	private lateinit var favorite: TextView
	private lateinit var historyBrowsing: TextView
	private lateinit var learnMinutes: TextView
	
	private lateinit var banner: CoBanner
	
	private lateinit var itemCourse: LinearLayout
	private lateinit var itemCollect: LinearLayout
	private lateinit var itemAddress: LinearLayout
	private lateinit var itemHistory: LinearLayout
	
	private lateinit var courseNotice: TextView
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_profile
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		userAvatar = view.findViewById(R.id.profile_user_avatar)
		username = view.findViewById(R.id.profile_username)
		loginDesc = view.findViewById(R.id.profile_login_desc)
		
		favorite = view.findViewById(R.id.profile_favorite)
		historyBrowsing = view.findViewById(R.id.profile_history_browsing)
		learnMinutes = view.findViewById(R.id.profile_learn_minutes)
		
		banner = view.findViewById(R.id.profile_banner)
		
		itemCourse = view.findViewById(R.id.item_course)
		itemCollect = view.findViewById(R.id.item_collect)
		itemAddress = view.findViewById(R.id.item_address)
		itemHistory = view.findViewById(R.id.item_history)
		
		courseNotice = view.findViewById(R.id.course_notice)
		
		queryLoginUserData()
		queryCourseNotice()
	}
	
	/**
	 * 查询课程通知
	 */
	private fun queryCourseNotice() {
		val ignoreInterceptor = listOf(HttpCodeInterceptor::class.java)
		ApiFactory.create(NoticeApi::class.java, ignoreInterceptor).notice().enqueue(object : CoCallback<CourseNotice> {
			override fun onSuccess(response: CoResponse<CourseNotice>) {
				if (response.isSuccessful()) {
					val data = response.data
					if (data != null) {
						val total = data.total
						courseNotice.visibility = if (total > 0) View.VISIBLE else View.GONE
						if (total > 99) {
							courseNotice.text = getString(R.string.profile_course_notice_count_geature_99)
						} else if (total > 0) {
							courseNotice.text = String.format("%d", total)
						}
					}
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
			}
		})
	}
	
	/**
	 * 查询登录用户数据
	 */
	private fun queryLoginUserData() {
		ApiFactory.create(AccountApi::class.java).profile().enqueue(object : CoCallback<UserProfile> {
			override fun onSuccess(response: CoResponse<UserProfile>) {
				val data = response.data
				if (response.isSuccessful() && data != null) {
					updateUI(data)
				} else {
					Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
				}
			}
			
			override fun onFailed(throwable: Throwable) {
				Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
			}
		})
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(userProfile: UserProfile) {
		if (userProfile.isLogin) {
			username.text = userProfile.userName
			loginDesc.text = getString(R.string.profile_login_desc_welcome_back)
			userAvatar.loadCircle(userProfile.userIcon)
		} else {
			username.text = getString(R.string.profile_please_login_first)
			loginDesc.text = getString(R.string.profile_please_login_first)
			userAvatar.loadCircle(R.drawable.ic_avatar_default)
			username.setOnClickListener {
				ARouter.getInstance().build(RoutePath.ACTIVITY_ACCOUNT_LOGIN).navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
			}
		}
		favorite.text = spannableTabItem(userProfile.favoriteCount, getString(R.string.profile_favorite))
		historyBrowsing.text = spannableTabItem(userProfile.browseCount, getString(R.string.profile_history_browsing))
		learnMinutes.text = spannableTabItem(userProfile.learnMinutes, getString(R.string.profile_learn_minutes))
		
		updateBanner(userProfile.bannerNoticeList)
	}
	
	/**
	 * 更新Banner
	 */
	private fun updateBanner(bannerNoticeList: List<Notice>?) {
		if (bannerNoticeList == null || bannerNoticeList.isEmpty()) return
		val models = mutableListOf<CoBannerMo>()
		bannerNoticeList.forEach {
			models += CoBannerMo(it.cover)
		}
		banner.setBannerData(R.layout.layout_profile_banner, models)
		banner.setBindAdapter { viewHolder, mo: CoBannerMo, _: Int ->
			val imageView = viewHolder.findViewById<ImageView>(R.id.banner_item_image_view)
			imageView.loadCorner(mo.url, BANNER_CORNER.dpInt)
		}
		banner.setOnBannerClickListener { _, _, position ->
			val url = bannerNoticeList[position].url
			CoRoute.startActivityForBrowser(url)
		}
		banner.visibility = View.VISIBLE
	}
	
	/**
	 * 设置字体样式
	 */
	private fun spannableTabItem(topText: Int, bottomText: String): CharSequence {
		val top = topText.toString()
		val ssb = SpannableStringBuilder()
		val ssTop = SpannableString(top)
		
		// 设置字体颜色
		ssTop.setSpan(
			ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
		)
		// 设置字体大小
		ssTop.setSpan(AbsoluteSizeSpan(18, true), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		// 设置粗细
		ssTop.setSpan(StyleSpan(Typeface.BOLD), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		
		ssb.append(ssTop)
		if (!bottomText.startsWith("\n")) {
			ssb.append("\n")
		}
		ssb.append(bottomText)
		return ssb
	}
	
	@Suppress("OVERRIDE_DEPRECATION")
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_CODE_LOGIN_PROFILE) {
			queryLoginUserData()
			queryCourseNotice()
		}
	}
}