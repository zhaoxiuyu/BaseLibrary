package com.base.library.entitys

/**
 * 作用：网络请求返回的数据状态类
 */
class BResponse<T> {

    // 数据体，失败描述，错误码，错误异常
    var data: T? = null
    var errorMsg: String = ""
    var errorCode: Int = 0
    var throwable: Throwable? = null

}