package com.base.app.entitys.response

class WanArticles {

    var chapterName: String? = null
    var link: String? = null
    var niceDate: String? = null
    var shareUser: String? = null
    var publishTime: String? = null

    var tags: MutableList<WanArticlesTags>? = null

    override fun toString(): String {
        return "WanArticles(chapterName=$chapterName, link=$link, niceDate=$niceDate, shareUser=$shareUser, publishTime=$publishTime, tags=$tags)"
    }

}