package com.base.library.rxhttp

import com.base.library.BuildConfig
import rxhttp.wrapper.param.*

/**
 * 通用的网络请求参数封装
 */
class RxRequest(val url: String) {

    // 网络请求是否弹出加载框
    var showLoading = true

    // 请求失败，是否弹窗提示
    var showFail = false

    // 请求成功，是否弹窗提示
    var showSuccess = false

    // 失败提示框，点击确定按钮，是否销毁当前页面
    var failClickFinish = false

    // 请求成功，点击确定按钮，是否销毁当前页面
    var successClickFinish = false

    // 用来判断当前是哪个请求接口,默认设置为url
    var method = url

    // 提示信息,加载 成功 失败 都用这个字段
    var msg = "请稍候..."

    // 用来发起请求的
    private var mRxHttp: RxHttp<*, *>? = null

    // 默认为 get 请求
    fun getRxHttp(): RxHttp<*, *> {
        return mRxHttp?.let { mRxHttp } ?: httpGet()
    }

    fun httpGet(): RxHttpGetParamEncrypt {
        val mEncrypt = RxHttp.getParamEncrypt(url)
        this.mRxHttp = mEncrypt
        return mEncrypt
    }

    fun httpPostForm(): RxHttpPostFormEncrypt {
        val mEncrypt = RxHttp.postFormEncrypt(url)
        this.mRxHttp = mEncrypt
        return mEncrypt
    }

    fun httpPostJson(): RxHttpPostJsonEncrypt {
        val mEncrypt = RxHttp.postJsonEncrypt(url)
        this.mRxHttp = mEncrypt
        return mEncrypt
    }

    fun httpPostJsonArray(): RxHttpPostJsonArrayEncrypt {
        val mEncrypt = RxHttp.postJsonArrayEncrypt(url)
        this.mRxHttp = mEncrypt
        return mEncrypt
    }

    // 可发送byte数组、文本、Uri、File等任意形式的RequestBody对象
    fun httpPostBody(): RxHttpBodyParam {
        val mEncrypt = RxHttp.postBody(url)
        this.mRxHttp = mEncrypt
        return mEncrypt
    }

    fun print(): String {
        return if (BuildConfig.DEBUG) {
            "RxRequest(url='$url', showFail=$showFail, showSuccess=$showSuccess, failClickFinish=$failClickFinish, successClickFinish=$successClickFinish, method='$method', msg='$msg')"
        } else {
            ""
        }
    }

}