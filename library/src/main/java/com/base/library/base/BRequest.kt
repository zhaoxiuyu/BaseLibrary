package com.base.library.base

import com.blankj.utilcode.util.StringUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.convert.StringConvert
import com.lzy.okgo.request.base.BodyRequest
import com.lzy.okgo.request.base.Request
import com.lzy.okrx2.adapter.ObservableBody
import io.reactivex.Observable
import java.lang.reflect.Type

/**
 * 通用的网络请求参数封装
 */
class BRequest(val url: String) {
    // url 请求的标志,用来唯一指定请求

    var httpType = POST //请求类型
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

    var gsonType: Type? = null // 解析类型

    /**
     * 使用OkHttp+Retrofit请求时这个参数才有效果
     * 这个参数可以不用管,默认走Retrofit初始化时设置的baseUrl
     * 如果请求有不同的BaseUrl,就可以改变这个值，会在拦截器里面自动替换成这个参数
     */
    var baseUrl: String = "default"

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
        val request: Request<String, out Request<*, *>> = when (httpType) {
            GET -> OkGo.get(url)
            POST -> OkGo.post(url)
            PUT -> OkGo.put(url)
            DELETE -> OkGo.delete(url)
            HEAD -> OkGo.head(url)
            else -> OkGo.options(url)
        }

        heads?.forEach { request.headers(it.key, it.value) }
        request.params(params)
        request.tag(tag)
        request.cacheMode(cacheMode)
        request.cacheTime(cacheTime)

        if (!StringUtils.isEmpty(body) || isSpliceUrl) {//是否强制将params的参数拼接到url后面,up系列与params系列混用
            (request as BodyRequest)
                .upString(body)
            request.upString(body)
            request.isSpliceUrl(isSpliceUrl)
            request.isMultipart(isMultipart) // 是否使用表单上传
        }

        return request
    }

    fun print(): String {
        val sb = StringBuilder()
        sb.appendln("请求地址 : $url")
        sb.appendln("请求方法 : $method")
        sb.appendln("params参数为 : ")
        params?.forEach { sb.appendln("${it.key} = ${it.value}") }
        sb.appendln("请求头为 : ")
        heads?.forEach { sb.appendln("${it.key} = ${it.value}") }
        sb.appendln("body参数为 : ")
        sb.appendln(body)

//        LogUtils.i(sb.toString())

        return sb.toString()
    }

    companion object {
        const val GET = 0x100000
        const val POST = 0x100001
        const val PUT = 0x100002
        const val DELETE = 0x100003
        const val HEAD = 0x100004
        const val OPTIONS = 0x100005
    }

}