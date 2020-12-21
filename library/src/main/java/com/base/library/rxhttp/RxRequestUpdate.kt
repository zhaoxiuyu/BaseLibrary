package com.base.library.rxhttp

import rxhttp.wrapper.param.*

/**
 * 通用的网络请求参数封装
 */
class RxRequestUpdate(val url: String) {

    // 网络请求是否弹出加载框
    private var silence = false

    // 请求失败，是否弹窗提示
    private var showFail = false

    // 请求成功，是否弹窗提示
    private var showSuccess = false

    // 失败提示框，点击确定按钮，是否销毁当前页面
    private var failClickFinish = false

    // 请求成功，点击确定按钮，是否销毁当前页面
    private var successClickFinish = false

    // 用来判断当前是哪个请求接口,默认设置为url
    private var method = url

    // 提示信息,加载 成功 失败 都用这个字段
    private var msg = "请稍候..."

    // 用来发起请求的
    private var mRxHttp: RxHttp<*, *>? = null

    fun silence(): RxRequestUpdate {
        silence = true
        return this
    }

    fun showFail(): RxRequestUpdate {
        showFail = true
        return this
    }

    fun showSuccess(): RxRequestUpdate {
        showSuccess = true
        return this
    }

    fun failClickFinish(): RxRequestUpdate {
        failClickFinish = true
        return this
    }

    fun successClickFinish(): RxRequestUpdate {
        successClickFinish = true
        return this
    }

    fun method(method: String): RxRequestUpdate {
        this.method = method
        return this
    }

    fun msg(msg: String): RxRequestUpdate {
        this.msg = msg
        return this
    }

    fun getRxHttp(): RxHttp<*, *>? {
        return mRxHttp
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

}