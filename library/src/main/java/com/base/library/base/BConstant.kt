package com.base.library.base

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 * 通用常量相关，比如存储的 KEY
 */
object BConstant {

    // 默认域名
    @DefaultDomain
    const val baseUrl = "https://www.baidu.com/"

    // 子域名,这个主要用来测试网络请求的
    @Domain(name = "wanandroid")
    const val wanandroidUrl = "https://www.wanandroid.com/"

    /**
     * 接口
     */
    // 首页文章列表
    const val article = "/article/list/1/json"

    // 公众号列表
    const val chapters = "/wxarticle/chapters/json"

    // 登录
    const val login = "/user/login"

    /**
     * 参数 key
     */
    const val LibraryToken = "LibraryToken"

}