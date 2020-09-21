package com.base.library.rxhttp

import rxhttp.wrapper.callback.Function

/**
 * 设置数据解密/解码器
 */
class RxHttpDecoder : Function<String, String> {

    // 每次请求成功，都会回调这里，并传入请求返回的密文
    override fun apply(t: String?): String {
        // 在这里将密文解密成明文，然后返回
        return t ?: ""
    }

}