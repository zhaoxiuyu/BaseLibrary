package com.base.library.entitys

import java.io.Serializable

/**
 * 作用: 接口返回的基类
 */
class BaseResponse : Serializable {

    var success: Boolean = false
    var message: String = ""
    var code: String = ""
    var data: Any? = null

}