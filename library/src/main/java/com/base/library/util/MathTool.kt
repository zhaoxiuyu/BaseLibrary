package com.base.library.util

import com.blankj.utilcode.util.StringUtils
import java.math.BigDecimal

object MathTool {

    //默认除法运算精度
    private const val DEF_DIV_SCALE = 10

    //这个类不能实例化
    private fun MathTool() {}

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    fun add(v1: Int, v2: Int): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    fun add(v1: Float, v2: Float): Double {
        val b1 = BigDecimal(v1.toDouble())
        val b2 = BigDecimal(v2.toDouble())
        return b1.add(b2).toDouble()
    }

    fun add(v1: Long, v2: Long): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    fun add(v1: String?, v2: String?): Double {
        var v1 = v1
        var v2 = v2
        if (StringUtils.isEmpty(v1)) {
            v1 = "0"
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0"
        }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: Int, v2: Int): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: Float, v2: Float): Double {
        val b1 = BigDecimal(v1.toDouble())
        val b2 = BigDecimal(v2.toDouble())
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: Long, v2: Long): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: String?, v2: String?): Double {
        var v1 = v1
        var v2 = v2
        if (StringUtils.isEmpty(v1)) {
            v1 = "0"
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0"
        }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: Int, v2: Int): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: Float, v2: Float): Double {
        val b1 = BigDecimal(v1.toDouble())
        val b2 = BigDecimal(v2.toDouble())
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: Long, v2: Long): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: String?, v2: String?): Double {
        var v1 = v1
        var v2 = v2
        if (StringUtils.isEmpty(v1)) {
            v1 = "0"
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0"
        }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    fun div(v1: Double, v2: Double): Double {
        return div(v1, v2, DEF_DIV_SCALE)
    }

    fun div(v1: Int, v2: Int): Double {
        return div(v1, v2, DEF_DIV_SCALE)
    }

    fun div(v1: Float, v2: Float): Double {
        return div(v1, v2, DEF_DIV_SCALE)
    }

    fun div(v1: Long, v2: Long): Double {
        return div(v1, v2, DEF_DIV_SCALE)
    }

    fun div(v1: String?, v2: String?): Double {
        return div(v1, v2, DEF_DIV_SCALE)
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    fun div(v1: Double, v2: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: Int, v2: Int, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: Float, v2: Float, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1.toDouble())
        val b2 = BigDecimal(v2.toDouble())
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: Long, v2: Long, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: String?, v2: String?, scale: Int): Double {
        var v1 = v1
        var v2 = v2
        if (StringUtils.isEmpty(v1)) {
            v1 = "0"
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "1"
        }
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun round(v: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun round(v: Int, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun round(v: Float, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v.toDouble())
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun round(v: Long, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun round(v: String?, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun roundStr(v: Double, scale: Int): String? {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
    }

    fun roundStr(v: Int, scale: Int): String? {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
    }

    fun roundStr(v: Float, scale: Int): String? {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v.toDouble())
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
    }

    fun roundStr(v: Long, scale: Int): String? {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
    }

    fun roundStr(v: String?, scale: Int): String? {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString()
    }
}