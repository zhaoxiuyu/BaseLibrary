package com.base.library.rxhttp

import rxhttp.wrapper.callback.Function
import rxhttp.wrapper.param.Param

/**
 * RxHttp支持为所有的请求添加公共参数/请求头，如果你希望某个请求不添加公共参数/请求头，也是支持的
 * 如果某个请求不添加公共参数/请求头，可以在RxRequest中设置assemblyEnabled=false
 *
 */
class RxHttpParamAssembly : Function<Param<*>, Param<*>> {

    override fun apply(t: Param<*>?): Param<*>? {
        // 此方法在子线程中执行，即请求发起线程

        // 请求方法，GET、POST等
        val method = t?.getMethod()

        // 可根据请求类型添加不同的参数
        when {
            method?.isGet == true -> {

            }
            method?.isPost == true -> {

            }
            else -> {

            }
        }

        // 添加公共参数
        // t?.add("", "")

        // 添加公共请求头
        // t?.addHeader("", "")

        return t
    }

}