package com.base.library.mvp

import com.base.library.base.BRequest
import com.blankj.utilcode.util.LogUtils

/**
 * 作用: 通用的P层实现
 * 如果你不想自定义P层，可以直接使用这个类
 */
class _VPPresenter(view: _VPView) : VPPresenterImpl<_VPView>(view), VPPresenter {

    override fun requestSuccess(body: String, bRequest: BRequest) {
        super.requestSuccess(body, bRequest)
        mView?.bindData(body)
    }

    override fun requestError(throwable: Throwable?, bRequest: BRequest) {
        mView?.disDialog()

        var string = "${throwable?.message}"
        LogUtils.e("错误信息 : \n ${throwable?.message}")

        mView?.bindError(string)
    }

}
