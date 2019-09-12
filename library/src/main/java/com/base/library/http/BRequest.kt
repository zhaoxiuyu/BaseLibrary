package com.base.library.http

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.convert.StringConvert
import com.lzy.okgo.request.base.BodyRequest
import com.lzy.okgo.request.base.Request
import com.lzy.okrx2.adapter.ObservableBody
import io.reactivex.Observable

/**
 * 通用的网络请求参数封装
 */
class BRequest(val url: String) {
    // url 请求的标志,用来唯一指定请求

    var httpType = POST //请求类型
    var httpMode = getOkGo //请求方式
    var silence = false //是否静默加载
    var isFinish = false//请求失败 确定 提示框 是否销毁当前页面
    var isSpliceUrl = false//是否强制将params的参数拼接到url后面,up系列与params系列混用
    var isMultipart = false//是否强制使用multipart/form-data表单上传
    var method: String = url //方法名(默认设置为URL)
    var cacheMode = CacheMode.NO_CACHE//缓存模式
    var cacheTime = -1L //缓存时长-1永不过期
    var heads: Map<String, String>? = null //请求头和参数
    var params: Map<String, String>? = null // key value 参数
    var body: String = "" //upString
    var tag: Any? = null //标识

    fun getOkGo(): Request<String, out Request<*, *>> {
        return getRequest()
    }

    fun getOkRx2(): Observable<String> {
        // val request = OkGo.post<String>(url)
        val request = getRequest()

        request.converter(StringConvert())
        return request.adapt(ObservableBody<String>())
    }

    private fun getRequest(): Request<String, out Request<*, *>> {
        val request: Request<String, out Request<*, *>>
        when (httpType) {
            GET -> request = OkGo.get(url)
            POST -> request = OkGo.post(url)
            PUT -> request = OkGo.put(url)
            DELETE -> request = OkGo.delete(url)
            HEAD -> request = OkGo.head(url)
            else -> request = OkGo.options(url)
        }
        heads?.forEach { request?.headers(it.key, it.value) }
        request?.params(params)
        request?.tag(tag)
        request?.cacheMode(cacheMode)
        request?.cacheTime(cacheTime)

        if (!StringUtils.isEmpty(body) || isSpliceUrl) {//是否强制将params的参数拼接到url后面,up系列与params系列混用
            (request as BodyRequest)
                .upString(body)
            request.upString(body)
            request.isSpliceUrl(isSpliceUrl)
            request.isMultipart(isMultipart) // 是否使用表单上传
        }

        return request
    }

    fun print() {
        val sb = StringBuilder()
        sb.appendln("请求地址 : $url")
        sb.appendln("请求方法 : $method")
        sb.appendln("params参数为 : ")
        params?.forEach { sb.appendln("${it.key} = ${it.value}") }
        sb.appendln("body参数为 : ")
        sb.appendln(body)
        sb.appendln("请求头为 : ")
        heads?.forEach { sb.appendln("${it.key} = ${it.value}") }
        LogUtils.i(sb.toString())
    }

    companion object {
        const val GET = 0x100000
        const val POST = 0x100001
        const val PUT = 0x100002
        const val DELETE = 0x100003
        const val HEAD = 0x100004
        const val OPTIONS = 0x100005

        const val getOkGo = 0x100006 // OkGo 请求方式
        const val getOkRx2 = 0x100007 // OkGo Rx2 请求方式
        const val getRetrofit2 = 0x100008 // Retrofit2 请求方式
    }

}