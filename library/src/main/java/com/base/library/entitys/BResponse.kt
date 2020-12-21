package com.base.library.entitys

/**
 * 作用：网络请求返回的数据状态类
 */
class BResponse<T> {

    // 数据体，失败描述，错误码，错误异常
    var data: T? = null
    var errorMsg: String? = null
    var errorCode: Int = -1

    var status: Int = -1
    var message: String? = null

    var code: String = ""
    var msg: String = ""

    fun isSuccess(): Boolean {
        return status == 200 || errorCode == 0 || code == "200"
    }

    fun showMsg(): String {
        return message ?: errorMsg ?: msg ?: ""
    }

}