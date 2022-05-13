package com.base.library.util

import com.base.library.base.BResponse
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object RxHttpUtils {

    fun <T> getDeftBResponse(c: Class<T>, t: Throwable? = null): BResponse<T> {
        return BResponse<T>().apply {
            errorMsg = getThrowableMessage(t)
        }
    }

    fun <T> getDeftBResponses(c: Class<T>, t: Throwable? = null): BResponse<MutableList<T>> {
        return BResponse<MutableList<T>>().apply {
            errorMsg = getThrowableMessage(t)
        }
    }

    fun getThrowableMessage(throwable: Throwable?): String {
        return if (throwable == null) {
            ""
        } else if (throwable is UnknownHostException) {
            // 通过 OkHttpClient 设置的超时 引发的异常
            if (NetworkUtils.isConnected()) "网络连接不可用" else "当前无网络"
        } else if (throwable is SocketTimeoutException || throwable is TimeoutException) {
            // 对单个请求调用 timeout 方法引发的超时异常
            "连接超时,请稍后再试"
        } else if (throwable is ConnectException) {
            "网络不给力,请稍候重试"
        }
//        else if (throwable is HttpStatusCodeException) {
//            val result = throwable.result
//            try {
//                val bResponse = GsonUtils.getGson().fromJson(result, BResponse::class.java)
//                bResponse.message ?: "请求异常"
//            } catch (e: Exception) {
//                throwable.message ?: "请求异常"
//            }
//        }
        else if (throwable is JsonSyntaxException) {
            //  请求成功,但Json语法异常,导致解析失败
            "数据解析失败,请稍后再试"
        }
//        else if (throwable is ParseException) {
//            //  ParseException异常表明请求成功，但是数据不正确
//            throwable.message ?: throwable.localizedMessage ?: throwable.errorCode ?: ""
//        }
        else {
            throwable?.message ?: "出现异常"
        }
    }

}