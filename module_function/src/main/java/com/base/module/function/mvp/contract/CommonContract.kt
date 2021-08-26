package com.base.module.function.mvp.contract

import com.base.library.mvp.VPPresenter
import com.base.library.mvvm.OnHandleCallback
import rxhttp.wrapper.param.RxHttp

/**
 * 作用: 通用的回调层
 * 如果你不想自定义Contract层，可以直接使用这个类
 */
interface CommonContract {

    interface View : OnHandleCallback {
        fun responseSuccess(data: String)
        fun responseError(msg: String)
    }

    interface Presenter : VPPresenter {
        fun requestData(rxHttp: RxHttp<*, *>)
    }

}