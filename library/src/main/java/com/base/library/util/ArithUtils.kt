package com.base.library.util

import java.math.BigDecimal
import java.text.DecimalFormat

//默认除法运算精度
private const val DEF_DIV_SCALE = 2

/**
 * 加法运算
 * v1 被加数
 * v2 加数
 */
fun ArithAdd(v1: Double, v2: Double): Double {
    val b1 = BigDecimal(v1.toString())
    val b2 = BigDecimal(v2.toString())
    return b1.add(b2).toDouble()
}

/**
 * 减法运算
 * v1 被减数
 * v2 减数
 */
fun ArithSubtract(v1: Double, v2: Double): Double {
    val b1 = BigDecimal(v1.toString())
    val b2 = BigDecimal(v2.toString())
    return b1.subtract(b2).toDouble()
}

/**
 * 乘法运算。
 * v1 被乘数
 * v2 乘数
 */
fun ArithMultiply(v1: Double, v2: Double): Double {
    val b1 = BigDecimal(v1.toString())
    val b2 = BigDecimal(v2.toString())
    return b1.multiply(b2).toDouble()
}

/**
 * 除不尽的情况时,由scale参数指定精度,以后的数字四舍五入
 * v1 被除数
 * v2 除数
 * scale 表示精确到小数点以后几位
 */
fun ArithDivide(v1: Double, v2: Double, scale: Int = DEF_DIV_SCALE): Double {
    val b1 = BigDecimal(v1.toString())
    val b2 = BigDecimal(v2.toString())
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
}

/**
 * 小数位四舍五入处理
 * v1 需要四舍五入的数字
 * scale 表示精确到小数点以后几位
 */
fun ArithRound(v1: Double, scale: Int = DEF_DIV_SCALE): Double {
    val b1 = BigDecimal(v1.toString())
    val b2 = BigDecimal("1")
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
}

//保留两位小数
fun ArithWith2(v1: Double): String {
    val df = DecimalFormat("#0.00")
    if (v1.equals(0)) {
        return "0.00"
    }
    return df.format(v1)
}