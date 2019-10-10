package com.base.library.util.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import java.util.concurrent.ExecutionException

/**
 * 加载图片
 */
fun GlideLoadImg(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(v)
}

/**
 * 加载图片,设置占位图
 */
fun GlideLoadImg(v: ImageView, url: String, holder: Int) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(holder)
        .error(holder)
        .fallback(holder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(v)
}

/**
 * 加载图片,设置占位图
 */
fun GlideLoadImg(v: ImageView, url: String, holder: Drawable) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(holder)
        .error(holder)
        .fallback(holder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(v)
}

/**
 * 加载图片,重新调整图片大小
 */
fun GlideLoadImg(v: ImageView, url: String, width: Int, height: Int) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .override(width, height)
        .into(v)
}

/**
 * 加载图片,为图片加入一些变化
 */
fun GlideLoadImg(v: ImageView, url: String, transformation: Transformation<Bitmap>) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .asBitmap()
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(BitmapTransitionOptions.withCrossFade())
        .transform(transformation)
        .into(v)
}

/**
 * 图片可能不完整
 * 缩放宽和高都到达View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能超过边界
 */
fun GlideLoadImgCenterCrop(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(v)
}

/**
 * 图片完整
 * 如果宽和高都在View的边界内，那就不缩放，否则缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
 */
fun GlideLoadImgCenterInside(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerInside()
        .into(v)
}

/**
 * 图片完整
 * 缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
 */
fun GlideLoadImgFitCenter(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(v)
}

/**
 * 加载圆形图片
 */
fun GlideLoadImgCircleCrop(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .into(v)
}

/**
 * 加载圆形图片
 */
fun GlideLoadImgCircleCrop(v: ImageView, url: String, holder: Int) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(holder)
        .error(holder)
        .fallback(holder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .into(v)
}

/**
 * 加载圆形图片
 */
fun GlideLoadImgCircleCrop(v: ImageView, url: String, holder: Drawable) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(holder)
        .error(holder)
        .fallback(holder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .into(v)
}

/**
 * 加载圆角图片
 */
fun GlideLoadImgRoundedCorners(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(RoundedCorners(11))
        .into(v)
}

/**
 * 加载圆角图片
 */
fun GlideLoadImgRoundedCorners(v: ImageView, url: String, roundingRadius: Int) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(RoundedCorners(roundingRadius))
        .into(v)
}

/**
 * 加载图片并且不缓存
 */
fun GlideLoadImgNoCache(v: ImageView, url: String) {
    LogUtils.i("IMG", url)
    GlideApp
        .with(v.context)
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(DrawableTransitionOptions.withCrossFade())
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(v)
}

/**
 * 加载图片返回Bitmap
 */
fun GlideLoadImgSimpleTarget(context: Context, url: String, target: SimpleTarget<Bitmap>) {
    GlideApp
        .with(context)
        .asBitmap()
        .load(url)
        .placeholder(ColorDrawable(-0xf0f10))
        .error(ColorDrawable(-0xf0f10))
        .fallback(ColorDrawable(-0xf0f10))
        .transition(BitmapTransitionOptions.withCrossFade())
        .into(target)
}

fun getGlideBitmap(url: String): Bitmap? {
    LogUtils.i("IMG", url)
    var bitmap: Bitmap? = null
    try {
        bitmap = GlideApp.with(Utils.getApp())
            .asBitmap()
            .load(url)
            .centerCrop()
            .into(500, 500)
            .get()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: ExecutionException) {
        e.printStackTrace()
    }

    return bitmap
}

fun getGlideBitmapPath(url: String): String {
    LogUtils.i("IMG", url)
    var path = ""
    val future = GlideApp.with(Utils.getApp()).load(url).downloadOnly(500, 500)
    try {
        val cacheFile = future.get()
        path = cacheFile.absolutePath
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: ExecutionException) {
        e.printStackTrace()
    }
    return path
}

/**
 * 清空图片缓存
 */
fun GlideDiskClean() {
    //磁盘缓存清理（子线程）
    GlideApp.get(Utils.getApp()).clearDiskCache()
}

fun GlideMemoryClean() {
    //内存缓存清理（主线程）
    GlideApp.get(Utils.getApp()).clearMemory()
}

