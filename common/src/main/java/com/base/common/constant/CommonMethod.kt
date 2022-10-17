package com.base.common.constant

/**
 * 测试用的静态方法
 */
object CommonMethod {

    // 根据传入的数量，返回数据量
    fun getStrs(count: Int = 10): MutableList<String> {
        val strs = mutableListOf<String>()
        for (index in 1..count) {
            strs.add("$index")
        }
        return strs
    }

}