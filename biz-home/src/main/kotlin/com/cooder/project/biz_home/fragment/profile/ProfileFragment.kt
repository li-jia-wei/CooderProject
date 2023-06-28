package com.cooder.project.biz_home.fragment.profile

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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.banner.core.CoBannerMo
import com.cooder.project.biz_home.R
import com.cooder.project.biz_home.databinding.FragmentProfileBinding
import com.cooder.project.common.route.CoRoute
import com.cooder.project.common.ui.component.CoBaseFragment
import com.cooder.project.common.ui.view.expends.loadCircle
import com.cooder.project.common.ui.view.expends.loadCorner
import com.cooder.project.pub_mod.model.Notice
import com.cooder.project.pub_mod.model.UserProfile
import com.cooder.project.service_login.LoginServiceProvider

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/3 23:47
 *
 * 介绍：个人中心Fragment
 */
class ProfileFragment : CoBaseFragment<FragmentProfileBinding>() {
	
	private val viewModel by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }
	
	private companion object {
		private const val BANNER_CORNER = 10
	}
	
	override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
		return FragmentProfileBinding.inflate(inflater, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		queryProfile()
		queryCourseNotice()
	}
	
	/**
	 * 查询课程通知
	 */
	private fun queryCourseNotice() {
		viewModel.queryCourseNotice().observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				val total = it.data!!.total
				binding.courseNotice.visibility = if (total > 0) View.VISIBLE else View.GONE
				if (total > 99) {
					binding.courseNotice.text = getString(R.string.profile_course_notice_count_geature_99)
				} else if (total > 0) {
					binding.courseNotice.text = String.format("%d", total)
				}
			} else {
				showToast(it.msg)
			}
		}
	}
	
	/**
	 * 查询登录用户数据
	 */
	private fun queryProfile() {
		viewModel.queryProfile().observe(viewLifecycleOwner) {
			if (it.isSuccessful()) {
				updateUI(it.data!!)
			} else {
				showToast(it.msg)
			}
		}
	}
	
	/**
	 * 更新UI
	 */
	private fun updateUI(userProfile: UserProfile) {
		if (userProfile.isLogin) {
			binding.username.text = userProfile.userName
			binding.loginDesc.text = getString(R.string.profile_login_desc_welcome_back)
			binding.userAvatar.loadCircle(userProfile.userIcon)
			binding.headerView.setOnClickListener(null)
		} else {
			binding.username.text = getString(R.string.profile_please_login_first)
			binding.loginDesc.text = getString(R.string.profile_please_login_first)
			binding.userAvatar.loadCircle(R.drawable.ic_avatar_default)
			binding.headerView.setOnClickListener {
				if (isAlive() && LoginServiceProvider.isNotLogin()) {
					LoginServiceProvider.toLogin(requireContext()) {
						queryProfile()
					}
				}
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
		if (bannerNoticeList.isNullOrEmpty()) return
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
}