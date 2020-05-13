package com.base.library.util.glide


import android.content.Context
import android.graphics.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import com.bumptech.glide.util.Util
import java.security.MessageDigest

/**
 * 作用：集圆角/圆形/蒙层一体
 */
class GlideCircleTransform(private val mBitmapPool: BitmapPool) : Transformation<Bitmap> {
    private var coverColor = -1
    private var radius: Float = 0.toFloat()
    private var exceptLeftTop: Boolean = false
    private var exceptRightTop: Boolean = false
    private var exceptLeftBottom: Boolean = false
    private var exceptRightBotoom: Boolean = false

    val id: String
        get() = this.javaClass.name

    /**
     * 除了那几个角不需要圆角的
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    fun setExceptCorner(
        leftTop: Boolean,
        rightTop: Boolean,
        leftBottom: Boolean,
        rightBottom: Boolean
    ) {
        this.exceptLeftTop = leftTop
        this.exceptRightTop = rightTop
        this.exceptLeftBottom = leftBottom
        this.exceptRightBotoom = rightBottom
    }

    constructor(context: Context) : this(Glide.get(context).bitmapPool) {}

    /**
     * @param coverColor 蒙层颜色
     */
    constructor(context: Context, coverColor: Int) : this(Glide.get(context).bitmapPool) {
        this.coverColor = coverColor
    }

    /**
     * @param coverColor 蒙层颜色
     * @param radius     圆角
     */
    constructor(
        context: Context,
        coverColor: Int,
        radius: Float
    ) : this(Glide.get(context).bitmapPool) {
        this.radius = radius
        this.coverColor = coverColor
    }

    init {
        this.radius = 0.0f
    }

    override fun transform(
        context: Context, resource: Resource<Bitmap>, outWidth: Int, outHeight: Int
    ): Resource<Bitmap> {
        val source = resource.get()
        var bitmap: Bitmap? = null
        val width: Int
        val height: Int
        var afterWidth: Int
        if (this.radius > 0.0f) { //设置圆角
            var afterHeight: Int
            val bili: Float
            val bili1: Float
            if (outWidth > outHeight) {
                bili = outHeight.toFloat() / outWidth.toFloat()
                afterWidth = source.width
                afterHeight = (source.width.toFloat() * bili).toInt()
                if (afterHeight > source.height) {
                    bili1 = outWidth.toFloat() / outHeight.toFloat()
                    afterHeight = source.height
                    afterWidth = (source.height.toFloat() * bili1).toInt()
                }
            } else if (outWidth < outHeight) {
                bili = outWidth.toFloat() / outHeight.toFloat()
                afterHeight = source.height
                afterWidth = (source.height.toFloat() * bili).toInt()
                if (afterWidth > source.width) {
                    bili1 = outHeight.toFloat() / outWidth.toFloat()
                    afterWidth = source.width
                    afterHeight = (source.width.toFloat() * bili1).toInt()
                }
            } else {
                afterHeight = source.height
                afterWidth = afterHeight
            }
            width = (source.width - afterWidth) / 2
            height = (source.height - afterHeight) / 2
            //修正圆角
            this.radius *= afterHeight.toFloat() / outHeight.toFloat()
            bitmap = this.mBitmapPool.get(afterWidth, afterHeight, Bitmap.Config.ARGB_8888)
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(afterWidth, afterHeight, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap!!)
            val paint = Paint()
            val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            if (width != 0 || height != 0) {
                val matrix = Matrix()
                matrix.setTranslate((-width).toFloat(), (-height).toFloat())
                shader.setLocalMatrix(matrix)
            }

            paint.shader = shader
            paint.isAntiAlias = true
            val rectF = RectF(0.0f, 0.0f, canvas.width.toFloat(), canvas.height.toFloat())
            canvas.drawRoundRect(rectF, this.radius, this.radius, paint)

            //设置蒙层
            val coverPaint = Paint()
            coverPaint.isAntiAlias = true
            coverPaint.color = coverColor
            if (coverColor > -1) {
                canvas.drawRoundRect(rectF, this.radius, this.radius, coverPaint)
            }


            if (exceptLeftTop) { //左上角不为圆角
                canvas.drawRect(0f, 0f, radius, radius, paint)
                if (coverColor > -1) {
                    canvas.drawRect(0f, 0f, radius, radius, coverPaint)
                }
            }
            if (exceptRightTop) {//右上角不为圆角
                canvas.drawRect(canvas.width - radius, 0f, canvas.width.toFloat(), radius, paint)
                if (coverColor > -1) {
                    canvas.drawRect(
                        canvas.width - radius, 0f, canvas.width.toFloat(), radius, coverPaint
                    )
                }
            }

            if (exceptLeftBottom) {//左下角不为圆角
                canvas.drawRect(0f, canvas.height - radius, radius, canvas.height.toFloat(), paint)
                if (coverColor > -1) {
                    canvas.drawRect(
                        0f,
                        canvas.height - radius,
                        radius,
                        canvas.height.toFloat(),
                        coverPaint
                    )
                }
            }

            if (exceptRightBotoom) {//右下角不为圆角
                canvas.drawRect(
                    canvas.width - radius,
                    canvas.height - radius, canvas.width.toFloat(), canvas.height.toFloat(), paint
                )
                if (coverColor > -1) {
                    canvas.drawRect(
                        canvas.width - radius,
                        canvas.height - radius,
                        canvas.width.toFloat(),
                        canvas.height.toFloat(),
                        coverPaint
                    )
                }
            }
        } else { //设置圆形
            width = Math.min(source.width, source.height)
            height = (source.width - width) / 2
            afterWidth = (source.height - width) / 2
            bitmap = this.mBitmapPool.get(width, width, Bitmap.Config.ARGB_8888)
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap!!)
            val paint = Paint()
            val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            if (height != 0 || afterWidth != 0) {
                val matrix = Matrix()
                matrix.setTranslate((-height).toFloat(), (-afterWidth).toFloat())
                shader.setLocalMatrix(matrix)
            }

            paint.shader = shader
            paint.isAntiAlias = true
            val r = width.toFloat() / 2.0f
            canvas.drawCircle(r, r, r, paint)
            //设置蒙层
            if (coverColor != -1) {
                val paint1 = Paint()
                paint1.isAntiAlias = true
                paint1.color = coverColor
                canvas.drawCircle(r, r, r, paint1)
            }
        }

        return BitmapResource.obtain(bitmap, this.mBitmapPool)!!
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    }

    override fun hashCode(): Int {
        //避免Transformation重复设置,导致图片闪烁,同一个圆角值的Transformation视为同一个对象
        return Util.hashCode(id.hashCode(), Util.hashCode(this.radius))
    }

}