package com.base.app.utils

object TestDatas {

    fun getStrs(count: Int = 10): MutableList<String> {
        val strs = mutableListOf<String>()
        for (index in 1..count) {
            strs.add("$index")
        }
        return strs
    }

}