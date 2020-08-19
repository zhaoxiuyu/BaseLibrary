package com.base.library.entitys

import rxhttp.wrapper.cahce.CacheMode
import rxhttp.wrapper.param.RxHttp

/**
 * 通用的网络请求参数封装
 */
class BRequest(val url: String, val httpType: Int = GET) {

    // url 请求的标志,用来唯一指定请求
    var silence = false //是否静默加载

    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面
    var assemblyEnabled = true  // 是否使用公共参数/请求头

    var cacheMode = CacheMode.ONLY_NETWORK //缓存模式
    var cacheTime = -1L  //缓存时长-1永不过期

    var heads: Map<String, String>? = null //请求头和参数
    var params: Map<String, String>? = null // key value 参数
    var json: String? = null //upString

    private lateinit var RXHttp: RxHttp<*, *>

    fun getRxHttp(): RxHttp<*, *> {
        return RXHttp
    }

    fun build(): BRequest {
        when (httpType) {
            PostForm -> PostForm()
            PostJson -> PostJson()
            PostJsonArray -> PostJsonArray()
            else -> Get()
        }
        return this
    }

    private fun Get(): BRequest {
//        val http = RxHttp.getParamEncrypt(url)
        val http = RxHttp.get(url)
        params?.let { http.addAll(it) }
        return HttpSetting(http)
    }

    private fun PostForm(): BRequest {
//        val http = RxHttp.postFormEncrypt(url)
        val http = RxHttp.postForm(url)
        params?.let { http.addAll(it) }
        return HttpSetting(http)
    }

    private fun PostJson(): BRequest {
//        val http = RxHttp.postJsonEncrypt(url)
        val http = RxHttp.postJson(url)
        params?.let { http.addAll(it) }
        json?.let { http.addAll(it) }
        return HttpSetting(http)
    }

    private fun PostJsonArray(): BRequest {
        val http = RxHttp.postJsonArray(url)
        params?.let { http.addAll(it) }
        json?.let { http.addJsonElement(it) }
        return HttpSetting(http)
    }

    private fun HttpSetting(http: RxHttp<*, *>): BRequest {
        RXHttp = http
        RXHttp.isAssemblyEnabled = assemblyEnabled
        RXHttp.addAllHeader(heads)
        RXHttp.setCacheValidTime(cacheTime)
        RXHttp.setCacheMode(cacheMode)
        return this
    }

    companion object {
        const val GET = 0x100000
        const val PostForm = 0x100001
        const val PostJson = 0x100002
        const val PostJsonArray = 0x100003
    }

}