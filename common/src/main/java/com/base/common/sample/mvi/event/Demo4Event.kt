package com.base.common.sample.mvi.event

import com.base.common.entitys.response.WanAndroid
import com.base.common.entitys.response.WanArticle
import com.base.common.entitys.response.WanChapters
import com.base.common.entitys.response.WanLogin

sealed class Demo4Event {
    var map: Map<String, String> = mapOf()

    fun setMap(map: Map<String, String>): Demo4Event {
        this.map = map
        return this
    }

    // 登录
    data class WanLoginM(var wanLogin: WanLogin? = null) : Demo4Event()

    // 首页文章列表
    data class WanArticleM(var mTriple: Triple<WanArticle?, MutableList<WanChapters>?, MutableList<WanAndroid>?>? = null) :
        Demo4Event()

    // 获取公众号列表
    data class WanChaptersM(var wanChapters: MutableList<WanChapters>? = null) : Demo4Event()

    // 首页banner
    data class WanAndroidM(var wanAndroid: List<WanAndroid>? = null) : Demo4Event()

    data class ShowLoading(var msg: String = "加载中") : Demo4Event()
    object DismissLoading : Demo4Event()
}
