package com.base.library.mvp

import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest
import com.blankj.utilcode.util.LogUtils

/**
 * 作用: 通用的P层实现
 */
class BasePresenter(view: BaseView) : BPresenterImpl<BaseView>(view), BPresenter {

    override fun requestSuccess(baseResponse: BaseResponse, baseHttpDto: BRequest) {
        mView?.disDialog()
        mView?.bindData(baseResponse)
    }

    override fun requestError(throwable: Throwable?, baseHttpDto: BRequest) {
        mView?.disDialog()

        var string = "${throwable?.message}"
        LogUtils.e("错误信息 : \n ${throwable?.message}")

        mView?.bindError(string)
    }

}
