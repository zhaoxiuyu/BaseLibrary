package com.base.library.rxhttp

import rxhttp.wrapper.cahce.CacheMode
import rxhttpLibrary.RxHttp

/**
 * 通用的网络请求参数封装
 */
class RxRequest(val url: String, val httpType: Int = GET) {

    // url 请求的标志,用来唯一指定请求
    var silence = false //是否静默加载

    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面
    var assemblyEnabled = true  // 是否使用公共参数/请求头

    var cacheMode = CacheMode.ONLY_NETWORK //缓存模式
    var cacheTime = -1L  //缓存时长-1永不过期

    var headMap: Map<String, String>? = null //请求头
    var paramMap: Map<String, String>? = null // key value 参数
    var jsonObj: String? = null // json对象
    var jsonObjElement: String? = null // jsonObjElement会转换成数组
    var objArray: ArrayList<*>? = null // 数组

    private lateinit var RXHttp: RxHttp<*, *>

    fun getRxHttp(): RxHttp<*, *> {
        return RXHttp
    }

    fun build(): RxRequest {
        when (httpType) {
            PostForm -> PostForm()
            PostJson -> PostJson()
            PostJsonArray -> PostJsonArray()
            else -> Get()
        }
        return this
    }

    private fun Get(): RxRequest {
        val http = RxHttp.getParamEncrypt(url)
//        val http = RxHttp.get(url)
        return HttpSetting(http)
    }

    private fun PostForm(): RxRequest {
        val http = RxHttp.postFormEncrypt(url)
//        val http = RxHttp.postForm(url)
        paramMap?.let { http.addAll(it) }
        return HttpSetting(http)
    }

    private fun PostJson(): RxRequest {
        val http = RxHttp.postJsonEncrypt(url)
//        val http = RxHttp.postJson(url)
        paramMap?.let { http.addAll(it) }
        jsonObj?.let { http.addAll(it) }
        return HttpSetting(http)
    }

    private fun PostJsonArray(): RxRequest {
        val http = RxHttp.postJsonArray(url)
        paramMap?.let { http.addAll(it) }
        jsonObj?.let { http.addAll(it) }
        objArray?.let { http.addAll(it) }
        jsonObjElement?.let { http.addJsonElement(it) }
        return HttpSetting(http)
    }

    private fun HttpSetting(http: RxHttp<*, *>): RxRequest {
        RXHttp = http
        RXHttp.isAssemblyEnabled = assemblyEnabled
        RXHttp.addAllHeader(headMap)
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