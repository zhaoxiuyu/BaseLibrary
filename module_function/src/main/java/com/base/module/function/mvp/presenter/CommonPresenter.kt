package com.base.module.function.mvp.presenter

import com.base.library.mvp.core.SuccessCall
import com.base.library.mvp.core.VPPresenterImpl
import com.base.module.function.mvp.contract.CommonContract
import com.base.library.rxhttp.RxRequest

/**
 * 作用: 通用的P层实现
 * 如果你不想自定义P层，可以直接使用这个类
 */
class CommonPresenter(view: CommonContract.View) : VPPresenterImpl<CommonContract.View>(view),
    CommonContract.Presenter {

    override fun requestData(bRequest: RxRequest) {
        getDataString(bRequest, object : SuccessCall<String> {
            override fun accept(bResponse: String) {
                mView?.responseData(bResponse)
            }
        })
    }

}
