package com.base.library.entitys

import java.io.Serializable

class BPageList<T> : Serializable {

    // 当前页数
    var curPage: Int = 0

    // 总页数
    var pageCount: Int = 0

    // 总条数
    var total: Int = 0

    var data: List<T>? = null

}