package com.base.library.entitys

import rxhttp.wrapper.param.RxHttp

/**
 * 通用的网络请求参数封装
 */
class BRequest(private val url: String, private val httpType: Int) {
    // url 请求的标志,用来唯一指定请求

    var silence = false //是否静默加载
    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面

    var assemblyEnabled = true  // 是否使用公共参数/请求头

    var cacheMode = rxhttp.wrapper.cahce.CacheMode.ONLY_NETWORK //缓存模式
    var cacheTime = -1L  //缓存时长-1永不过期

    var heads: Map<String, String>? = null //请求头和参数
    var params: Map<String, String>? = null // key value 参数
    var json: String? = null //upString

    lateinit var getRxHttp: RxHttp<*, *>

    init {
        when (httpType) {
            PostForm -> PostForm()
            PostJson -> PostJson()
            PostJsonArray -> PostJsonArray()
            else -> Get()
        }
    }

    private fun Get(): BRequest {
        val rxHttp = RxHttp.get(url)
        params?.let { rxHttp.addAll(it) }
        return HttpSetting(rxHttp)
    }

    private fun PostForm(): BRequest {
        val rxHttp = RxHttp.postForm(url)
        params?.let { rxHttp.addAll(it) }
        return HttpSetting(rxHttp)
    }

    private fun PostJson(): BRequest {
        val rxHttp = RxHttp.postJson(url)
        params?.let { rxHttp.addAll(it) }
        json?.let { rxHttp.addAll(it) }
        return HttpSetting(rxHttp)
    }

    private fun PostJsonArray(): BRequest {
        val rxHttp = RxHttp.postJsonArray(url)
        params?.let { rxHttp.addAll(it) }
        json?.let { rxHttp.addJsonElement(it) }
        return HttpSetting(rxHttp)
    }

    private fun HttpSetting(rxHttp: RxHttp<*, *>): BRequest {
        getRxHttp = rxHttp
        getRxHttp.isAssemblyEnabled = assemblyEnabled
        getRxHttp.addAllHeader(heads)
        getRxHttp.setCacheValidTime(cacheTime)
        getRxHttp.setCacheMode(cacheMode)
        return this
    }

    companion object {
        const val GET = 0x100000
        const val PostForm = 0x100001
        const val PostJson = 0x100002
        const val PostJsonArray = 0x100003
    }

}