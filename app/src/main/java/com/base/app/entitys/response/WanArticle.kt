package com.base.app.entitys.response

class WanArticle {

    var offset: String? = null
    var over: String? = null
    var pageCount: String? = null
    var size: String? = null
    var total: String? = null
    var curPage: String? = null

    var datas: MutableList<WanArticles>? = null

    override fun toString(): String {
        return "WanArticle(offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total, curPage=$curPage, datas=$datas)"
    }

}