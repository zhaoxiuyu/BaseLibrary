package com.base.library.entitys

import java.io.Serializable

/**
 * 作用: 接口返回的基类
 */
class BaseResponse : Serializable {

    var success: Boolean = false

    var msg: String? = null
    var message: String? = null

    var retCode: String? = null
    var code: String? = null

    var result: Any? = null
    var dataInfo: Any? = null

}