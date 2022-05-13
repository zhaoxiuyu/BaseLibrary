package com.base.library.mvvm

import java.io.Serializable

enum class DataStatus : Serializable {

    LOADING, // 正在加载
    LOADED, // 已加载
    EMPTY,  // 空数据
    ERROR,   // 错误

}