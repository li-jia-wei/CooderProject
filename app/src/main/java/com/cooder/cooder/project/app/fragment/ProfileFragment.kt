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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cooder.cooder.library.restful.CoCallback
import com.cooder.cooder.library.restful.CoResponse
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.project.app.R
import com.cooder.cooder.project.app.databinding.FragmentProfileBinding
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
class ProfileFragment : CoBaseFragment<FragmentProfileBinding>() {
	
	private companion object {
		private const val REQUEST_CODE_LOGIN_PROFILE = 1001
		
		private const val BANNER_CORNER = 10
	}
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
		return FragmentProfileBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
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
						binding.courseNotice.visibility = if (total > 0) View.VISIBLE else View.GONE
						if (total > 99) {
							binding.courseNotice.text = getString(R.string.profile_course_notice_count_geature_99)
						} else if (total > 0) {
							binding.courseNotice.text = String.format("%d", total)
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
			binding.username.text = userProfile.userName
			binding.loginDesc.text = getString(R.string.profile_login_desc_welcome_back)
			binding.userAvatar.loadCircle(userProfile.userIcon)
		} else {
			binding.username.text = getString(R.string.profile_please_login_first)
			binding.loginDesc.text = getString(R.string.profile_please_login_first)
			binding.userAvatar.loadCircle(R.drawable.ic_avatar_default)
			binding.headerView.setOnClickListener {
				if (isNotAlive()) return@setOnClickListener
				CoRoute.startActivity(RoutePath.ACTIVITY_BIZ_ACCOUNT_LOGIN, context = requireContext(), requestCode = REQUEST_CODE_LOGIN_PROFILE)
			}
		}
		binding.favorite.text = spannableTabItem(userProfile.favoriteCount, getString(R.string.profile_favorite))
		binding.historyBrowsing.text = spannableTabItem(userProfile.browseCount, getString(R.string.profile_history_browsing))
		binding.learnMinutes.text = spannableTabItem(userProfile.learnMinutes, getString(R.string.profile_learn_minutes))
		
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
		binding.banner.setBannerData(R.layout.item_profile_banner, models)
		binding.banner.setBindAdapter { viewHolder, mo: CoBannerMo, _: Int ->
			val imageView = viewHolder.findViewById<ImageView>(R.id.banner_item_image_view)
			imageView.loadCorner(mo.url, BANNER_CORNER.dpInt)
		}
		binding.banner.setOnBannerClickListener { _, _, position ->
			val url = bannerNoticeList[position].url
			CoRoute.startActivityForBrowser(url)
		}
		binding.banner.visibility = View.VISIBLE
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