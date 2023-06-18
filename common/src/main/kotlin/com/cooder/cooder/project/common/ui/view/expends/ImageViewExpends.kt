@file:JvmName("ImageViewExpends")

package com.cooder.cooder.project.common.ui.view.expends

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cooder.cooder.library.util.CoViewUtil
import com.cooder.cooder.project.common.R
import kotlin.math.min

/**
 * 加载图片
 */
fun ImageView.load(url: String) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(url)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载图片
 * @param callback 回调一个Drawable，
 */
fun ImageView.load(url: String, callback: (Drawable) -> Boolean) {
	if (CoViewUtil.isActivityDestroy(context)) return
	var canCallback = true
	Glide.with(this)
		.load(url)
		.listener(object : RequestListener<Drawable> {
			override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
				return false
			}
			
			override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
				if (canCallback) {
					resource?.let {
						canCallback = callback.invoke(it)
					}
				}
				return false
			}
		})
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载图片
 */
fun ImageView.load(@DrawableRes resId: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载图片
 */
fun ImageView.load(url: String, width: Int, height: Int) {
	Glide.with(this)
		.load(url)
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载图片
 */
fun ImageView.load(@DrawableRes resId: Int, width: Int, height: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircle(url: String) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(url)
		.transform(CircleCrop())
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircle(@DrawableRes resId: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CircleCrop())
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircle(url: String, width: Int, height: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(url)
		.transform(CircleCrop())
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircle(@DrawableRes resId: Int, width: Int, height: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CircleCrop())
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆角图片
 */
@BindingAdapter(value = ["url", "corner"], requireAll = false)
fun ImageView.loadCorner(url: String, @IntRange(from = 0) corner: Int) {
	if (corner > 0) {
		if (CoViewUtil.isActivityDestroy(context)) return
		// fix: 需要先裁剪再设置圆角，否则可能会导致被设置的圆角被裁剪
		Glide.with(this)
			.load(url)
			.transform(CenterCrop(), RoundedCorners(corner))
			.error(R.drawable.ic_load_failed)
			.into(this)
	} else {
		this.load(url)
	}
}

/**
 * 加载圆角图片
 */
fun ImageView.loadCorner(@DrawableRes resId: Int, @IntRange(from = 0) corner: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CenterCrop(), RoundedCorners(corner))
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadCorner(url: String, @IntRange(from = 0) corner: Int, width: Int, height: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	// fix: 需要先裁剪再设置圆角，否则可能会导致被设置的圆角被裁剪
	Glide.with(this)
		.load(url)
		.transform(CenterCrop(), RoundedCorners(corner))
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadCorner(@DrawableRes resId: Int, @IntRange(from = 0) corner: Int, width: Int, height: Int) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CenterCrop(), RoundedCorners(corner))
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形描边图片
 */
@JvmOverloads
fun ImageView.loadCircleBorder(url: String, @FloatRange(from = 0.0) borderWidth: Float = 0F, borderColor: Int = Color.WHITE) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(url)
		.transform(CircleBorderCrop(borderWidth, borderColor))
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形描边图片
 */
@JvmOverloads
fun ImageView.loadCircleBorder(@DrawableRes resId: Int, @FloatRange(from = 0.0) borderWidth: Float = 0F, borderColor: Int = Color.WHITE) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CircleBorderCrop(borderWidth, borderColor))
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形描边图片
 */
@JvmOverloads
fun ImageView.loadCircleBorder(url: String, width: Int, height: Int, @FloatRange(from = 0.0) borderWidth: Float = 0F, borderColor: Int = Color.WHITE) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(url)
		.transform(CircleBorderCrop(borderWidth, borderColor))
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 加载圆形描边图片
 */
@JvmOverloads
fun ImageView.loadCircleBorder(@DrawableRes resId: Int, width: Int, height: Int, @FloatRange(from = 0.0) borderWidth: Float = 0F, borderColor: Int = Color.WHITE) {
	if (CoViewUtil.isActivityDestroy(context)) return
	Glide.with(this)
		.load(resId)
		.transform(CircleBorderCrop(borderWidth, borderColor))
		.override(width, height)
		.error(R.drawable.ic_load_failed)
		.into(this)
}

/**
 * 圆形描边
 */
private class CircleBorderCrop(
	private val borderWidth: Float,
	borderColor: Int
) : CircleCrop() {
	
	private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
	
	init {
		borderPaint.color = borderColor
		borderPaint.style = Paint.Style.STROKE
		borderPaint.strokeWidth = borderWidth
	}
	
	/**
	 * 转换
	 */
	override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
		val transform: Bitmap = super.transform(pool, toTransform, outWidth, outHeight)
		val canvas = Canvas(transform)
		val halfWidth = outWidth / 2F
		val halfHeight = outHeight / 2F
		canvas.drawCircle(halfWidth, halfHeight, min(halfWidth, halfHeight) - borderWidth / 2F, borderPaint)
		canvas.setBitmap(null)
		return transform
	}
}