package com.base.library.mvp.template.contract

import com.base.library.entitys.BRequest
import com.base.library.mvp.core.VPPresenter
import com.base.library.mvp.core.VPView

/**
 * 作用: 通用的回调层
 * 如果你不想自定义Contract层，可以直接使用这个类
 */
interface CommonContract : VPView {

    interface View : VPView {
        fun responseData(data: String)
    }

    interface Presenter : VPPresenter {
        fun requestData(bRequest: BRequest)
    }

}