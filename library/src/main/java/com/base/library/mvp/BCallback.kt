package com.base.library.mvp

import android.util.Log
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request

open class BCallback(private val callback: BRequestCallback, private val silence: Boolean) : AbsCallback<String>() {

    /**
     * 请求网络开始前，UI线程
     */
    override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
        Log.v("OkGo", "onStart")
        if (!silence) callback.beforeRequest()
        super.onStart(request)
    }

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    override fun onSuccess(response: Response<String>?) {
        Log.v("OkGo", "onSuccess")
    }

    /**
     * 缓存成功的回调,UI线程
     */
    override fun onCacheSuccess(response: Response<String>?) {
        Log.v("OkGo", "onCacheSuccess")
        onSuccess(response)
        super.onCacheSuccess(response)
    }

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    override fun onError(response: Response<String>?) {
        Log.v("OkGo", "onError")
        super.onError(response)
    }

    /**
     * 请求网络结束后，UI线程
     */
    override fun onFinish() {
        Log.v("OkGo", "onFinish")
        callback.requestComplete()
        super.onFinish()
    }

    /**
     * 上传过程中的进度回调，get请求不回调，UI线程
     */
    override fun uploadProgress(progress: Progress?) {
        Log.v("OkGo", "uploadProgress")
        super.uploadProgress(progress)
    }

    /**
     * 下载过程中的进度回调，UI线程
     */
    override fun downloadProgress(progress: Progress?) {
        Log.v("OkGo", "downloadProgress")
        super.downloadProgress(progress)
    }

    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     */
    override fun convertResponse(response: okhttp3.Response?): String {
        Log.v("OkGo", "convertResponse")
        return response?.body()?.string() ?: ""
    }

}