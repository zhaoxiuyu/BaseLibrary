package com.base.library.data.http

import com.base.library.base.BConstant
import rxhttp.wrapper.param.RxHttp

/**
 * 网络数据源
 */
class HttpDataSourceImpl {

    companion object {
        val getInstance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = HttpDataSourceImpl()
    }

    /**
     * 首页文章列表
     */
    fun getArticle(): RxHttp<*, *> {
        return RxHttp.getParamEncrypt(BConstant.article).setDomainTowanandroidIfAbsent()
    }

    /**
     * 获取公众号列表
     */
    fun getChapters() = RxHttp.getParamEncrypt(BConstant.chapters).setDomainTowanandroidIfAbsent()

    /**
     * 登录
     */
    fun getLogin(map: Map<String, String>) = RxHttp.postFormEncrypt(BConstant.login)
        .setDomainTowanandroidIfAbsent().addAll(map)

    /**
     * 首页banner
     */
    fun getBanner() = RxHttp.getParamEncrypt(BConstant.banner).setDomainTowanandroidIfAbsent()

}