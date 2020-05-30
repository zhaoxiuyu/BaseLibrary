package com.base.library.base

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain

/**
 * 通用常量相关，比如存储的 KEY
 */
object BConstant {

    // 默认域名
//    @DefaultDomain
//    const val baseUrl = "https://www.baidu.com/"

    // 子域名
    @Domain(name = "wanandroid")
    const val baiduUrl = "https://www.wanandroid.com/"

    // 子域名
    @Domain(name = "baiduUrl")
    const val sendinfoUrl = "https://www.baidu.com/"

    // 首页文章列表
    const val article = "/article/list/1/json"

    // 公众号列表
    const val chapters = "/wxarticle/chapters/json"

    // 登录
    const val login = "/user/login"

}