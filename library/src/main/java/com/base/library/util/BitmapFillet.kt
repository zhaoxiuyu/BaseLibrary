package com.base.library.util

import android.graphics.*
import java.lang.Exception

/**
 * 指定角切圆角
 */

private const val ALL = 347120
private const val TOP = 547120
private const val LEFT = 647120
private const val RIGHT = 747120
private const val BOTTOM = 847120

/**
 * type : ALL-TOP-LEFT-RIGHT-BOTTOM
 * bitmap : 需要被切圆角的图片
 * roundPx : 要切的像素大小
 * 原理 : 先建立一个与图片大小相同的透明的Bitmap画板,然后在画板上画出一个想要的形状区域,最后把原图片贴上
 */
fun bitmapFillet(type: Int, bitmap: Bitmap, roundPx: Int): Bitmap {
    try {
        val width = bitmap.width
        val height = bitmap.height

        val paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(paintingBoard)
        canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK

        when (type) {
            TOP -> clipTop(canvas, paint, roundPx, width, height)
            LEFT -> clipLeft(canvas, paint, roundPx, width, height)
            RIGHT -> clipRight(canvas, paint, roundPx, width, height)
            BOTTOM -> clipBottom(canvas, paint, roundPx, width, height)
            else -> clipAll(canvas, paint, roundPx, width, height)
        }

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //帖子图
        val src = Rect(0, 0, width, height)
        canvas.drawBitmap(bitmap, src, src, paint)
        return paintingBoard
    } catch (e: Exception) {
        return bitmap
    }
}

private fun clipLeft(canvas: Canvas, paint: Paint, offset: Int, width: Int, height: Int) {
    val block = Rect(offset, 0, width, height)
    canvas.drawRect(block, paint)
    val rectF = RectF(0f, 0f, (offset * 2).toFloat(), height.toFloat())
    canvas.drawRoundRect(rectF, offset.toFloat(), offset.toFloat(), paint)
}

private fun clipRight(canvas: Canvas, paint: Paint, offset: Int, width: Int, height: Int) {
    val block = Rect(0, 0, width - offset, height)
    canvas.drawRect(block, paint)
    val rectF = RectF((width - offset * 2).toFloat(), 0f, width.toFloat(), height.toFloat())
    canvas.drawRoundRect(rectF, offset.toFloat(), offset.toFloat(), paint)
}

private fun clipTop(canvas: Canvas, paint: Paint, offset: Int, width: Int, height: Int) {
    val block = Rect(0, offset, width, height)
    canvas.drawRect(block, paint)
    val rectF = RectF(0f, 0f, width.toFloat(), (offset * 2).toFloat())
    canvas.drawRoundRect(rectF, offset.toFloat(), offset.toFloat(), paint)
}

private fun clipBottom(canvas: Canvas, paint: Paint, offset: Int, width: Int, height: Int) {
    val block = Rect(0, 0, width, height - offset)
    canvas.drawRect(block, paint)
    val rectF = RectF(0f, (height - offset * 2).toFloat(), width.toFloat(), height.toFloat())
    canvas.drawRoundRect(rectF, offset.toFloat(), offset.toFloat(), paint)
}

private fun clipAll(canvas: Canvas, paint: Paint, offset: Int, width: Int, height: Int) {
    val rectF = RectF(0F, 0f, width.toFloat(), height.toFloat())
    canvas.drawRoundRect(rectF, offset.toFloat(), offset.toFloat(), paint)
}