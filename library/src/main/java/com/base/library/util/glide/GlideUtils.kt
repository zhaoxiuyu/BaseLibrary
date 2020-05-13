package com.base.library.util.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import java.util.concurrent.ExecutionException

/**
 * 作用: GlideUtils
 * 文档: https://muyangmin.github.io/glide-docs-cn/
 */
object GlideUtils {

    // --------------- 加载图片,不涉及图像改变 ---------------

    /**
     * 加载图片
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImg(v: ImageView, url: String) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(ColorDrawable(-0xf0f10))
            .error(ColorDrawable(-0xf0f10))
            .fallback(ColorDrawable(-0xf0f10))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(v)
    }

    /**
     * 加载图片,设置占位图
     *
     * @param v      ImageView
     * @param url    图片URL
     * @param holder 占位图
     */
    fun loadImg(v: ImageView, url: String, holder: Int) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(v)
    }

    /**
     * 加载图片,设置占位图
     *
     * @param v      ImageView
     * @param url    图片URL
     * @param holder 占位图
     */
    fun loadImg(v: ImageView, url: String, holder: Drawable) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(v)
    }

    /**
     * 加载图片,重新调整图片大小
     *
     * @param v      ImageView
     * @param url    图片URL
     * @param width  图片的宽
     * @param height 图片的高
     */
    fun loadImg(v: ImageView, url: String, width: Int, height: Int) {
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
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param transformation 变化
     */
    fun loadImg(v: ImageView, url: String, transformation: Transformation<Bitmap>) {
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
     * 加载图片,为图片加入一些变化
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param transformation 变化
     */
    fun loadImgTransparent(v: ImageView, url: String, transformation: Transformation<Bitmap>) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .asBitmap()
            .load(url)
            .placeholder(ColorDrawable(Color.TRANSPARENT))
            .error(ColorDrawable(Color.TRANSPARENT))
            .fallback(ColorDrawable(Color.TRANSPARENT))
            .transition(BitmapTransitionOptions.withCrossFade())
            .transform(transformation)
            .into(v)
    }


    // --------------- 加载图片,图片可能不完整 ---------------


    /**
     * 图片可能不完整
     * 缩放宽和高都到达View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能超过边界
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterCrop(v: ImageView, url: String) {
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
     * 图片可能不完整
     * 缩放宽和高都到达View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能超过边界
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterCrop(v: ImageView, url: String, holder: Int) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(v)
    }

    /**
     * 图片可能不完整
     * 缩放宽和高都到达View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能超过边界
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterCrop(v: ImageView, url: String, holder: Drawable) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(v)
    }


    // --------------- 图片完整,但是会露出背景 ---------------

    /**
     * 图片完整
     * 如果宽和高都在View的边界内，那就不缩放，否则缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterInside(v: ImageView, url: String) {
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
     * 如果宽和高都在View的边界内，那就不缩放，否则缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterInside(v: ImageView, url: String, holder: Drawable) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerInside()
            .into(v)
    }

    /**
     * 图片完整
     * 如果宽和高都在View的边界内，那就不缩放，否则缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCenterInside(v: ImageView, url: String, holder: Int) {
        LogUtils.i("IMG", url)
        GlideApp
            .with(v.context)
            .load(url)
            .placeholder(holder)
            .error(holder)
            .fallback(holder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerInside()
            .into(v)
    }

    // --------------- 图片完整但是可能变形 ---------------

    /**
     * 图片完整
     * 缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgFitCenter(v: ImageView, url: String) {
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
     * 图片完整
     * 缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgFitCenter(v: ImageView, url: String, holder: Drawable) {
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
     * 图片完整
     * 缩放宽和高都进入View的边界，有一个参数在边界上，另一个参数可能在边界上，也可能在边界内
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgFitCenter(v: ImageView, url: String, holder: Int) {
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

    // --------------- 加载圆形图片 ---------------

    /**
     * 加载圆形图片
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgCircleCrop(v: ImageView, url: String) {
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
     *
     * @param v      ImageView
     * @param url    图片URL
     * @param holder 占位图
     */
    fun loadImgCircleCrop(v: ImageView, url: String, holder: Int) {
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
     *
     * @param v      ImageView
     * @param url    图片URL
     * @param holder 占位图
     */
    fun loadImgCircleCrop(v: ImageView, url: String, holder: Drawable) {
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

    // --------------- 加载圆角图片 ---------------

    /**
     * 加载圆角图片
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgRoundedCorners(v: ImageView, url: String) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, 11f)
        transform.setExceptCorner(true, true, true, true)
        loadImgTransparent(v, url, transform)
    }

    /**
     * 加载圆角图片
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param roundingRadius 元角度数(px)
     */
    fun loadImgRoundedCorners(v: ImageView, url: String, roundingRadius: Int) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, roundingRadius.toFloat())
        transform.setExceptCorner(true, true, true, true)
        loadImgTransparent(v, url, transform)
    }

    /**
     * 加载圆角图片
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param roundingRadius 元角度数(px)
     */
    fun loadImgRoundedCornersRight(v: ImageView, url: String, roundingRadius: Int) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, roundingRadius.toFloat())
        transform.setExceptCorner(true, false, true, false)
        loadImgTransparent(v, url, transform)
    }

    /**
     * 加载圆角图片
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param roundingRadius 元角度数(px)
     */
    fun loadImgRoundedCornersLeft(v: ImageView, url: String, roundingRadius: Int) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, roundingRadius.toFloat())
        transform.setExceptCorner(false, true, false, true)
        loadImgTransparent(v, url, transform)
    }

    /**
     * 加载圆角图片
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param roundingRadius 元角度数(px)
     */
    fun loadImgRoundedCornersBottom(v: ImageView, url: String, roundingRadius: Int) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, roundingRadius.toFloat())
        transform.setExceptCorner(true, true, false, false)
        loadImgTransparent(v, url, transform)
    }

    /**
     * 加载圆角图片
     *
     * @param v              ImageView
     * @param url            图片URL
     * @param roundingRadius 元角度数(px)
     */
    fun loadImgRoundedCornersTop(v: ImageView, url: String, roundingRadius: Int) {
        LogUtils.i("IMG", url)
        val transform = GlideCircleTransform(v.context, Color.TRANSPARENT, roundingRadius.toFloat())
        transform.setExceptCorner(false, false, true, true)
        loadImgTransparent(v, url, transform)
    }


    /**
     * 加载图片并且不缓存
     *
     * @param v   ImageView
     * @param url 图片URL
     */
    fun loadImgNoCache(v: ImageView, url: String) {
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
     *
     * @param context 上下文
     * @param url     图片URL
     * @param target  目标
     */
    fun loadImageSimpleTarget(context: Context, url: String, target: SimpleTarget<Bitmap>) {
        LogUtils.i("IMG", url)
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

    /**
     * 加载视频的第一帧
     *
     * @param v        ImageView
     * @param videoUrl 视频URL
     */
    fun loadVideoImg(v: ImageView, videoUrl: String) {
        LogUtils.i("IMG", videoUrl)
        GlideApp
            .with(v.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(0)
                    .centerCrop()
                    .error(ColorDrawable(Color.BLACK))
                    .fallback(ColorDrawable(Color.BLACK))
                    .placeholder(ColorDrawable(Color.BLACK))
            )

            .load(videoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(v)
    }

    /**
     * 加载视频的第一帧
     *
     * @param v        ImageView
     * @param videoUrl 视频URL
     * @param holder   占位图
     */
    fun loadVideoImg(v: ImageView, videoUrl: String, holder: Int) {
        LogUtils.i("IMG", videoUrl)
        GlideApp
            .with(v.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(0)
                    .centerCrop()
                    .error(holder)
                    .fallback(holder)
                    .placeholder(holder)
            )

            .load(videoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(v)
    }

    /**
     * 加载视频图片
     *
     * @param v               ImageView
     * @param videoUrl        视频URL
     * @param holder          占位图
     * @param frameTimeMicros 视频多少秒
     */
    fun loadVideoImg(v: ImageView, videoUrl: String, holder: Int, frameTimeMicros: Long) {
        LogUtils.i("IMG", videoUrl)
        GlideApp
            .with(v.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(frameTimeMicros)
                    .centerCrop()
                    .error(holder)
                    .fallback(holder)
                    .placeholder(holder)
            )

            .load(videoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(v)
    }

    /**
     * 加载视频图片
     *
     * @param v               ImageView
     * @param videoUrl        视频URL
     * @param holder          占位图
     * @param frameTimeMicros 视频多少秒
     */
    fun loadVideoImg(v: ImageView, videoUrl: String, holder: Drawable, frameTimeMicros: Long) {
        LogUtils.i("IMG", videoUrl)
        GlideApp
            .with(v.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(frameTimeMicros)
                    .centerCrop()
                    .error(holder)
                    .fallback(holder)
                    .placeholder(holder)
            )

            .load(videoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(v)
    }

    fun getBitmap(url: String): Bitmap? {
        LogUtils.i("IMG", url)
        var bitmap: Bitmap? = null
        try {
            bitmap = GlideApp
                .with(Utils.getApp())
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

    fun getBitmapPath(url: String): String {
        LogUtils.i("IMG", url)
        var path = ""
        val future = GlideApp
            .with(Utils.getApp())
            .load(url)
            .downloadOnly(500, 500)
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
    fun clean() {
        //磁盘缓存清理（子线程）
        GlideApp.get(Utils.getApp()).clearDiskCache()
        //内存缓存清理（主线程）
        GlideApp.get(Utils.getApp()).clearMemory()
    }
}