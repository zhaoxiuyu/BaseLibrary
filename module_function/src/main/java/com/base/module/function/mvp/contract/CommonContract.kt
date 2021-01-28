package com.base.module.function.mvp.contract

import com.base.library.mvp.core.VPPresenter
import com.base.library.mvp.core.VPView
import com.base.library.rxhttp.RxRequest

/**
 * 作用: 通用的回调层
 * 如果你不想自定义Contract层，可以直接使用这个类
 */
interface CommonContract {

    interface View : VPView {
        fun responseData(data: String)
    }

    interface Presenter : VPPresenter {
        fun requestData(bRequest: RxRequest)
    }

}