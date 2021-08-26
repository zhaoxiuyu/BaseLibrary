package com.base.module.function.mvp.presenter

import com.base.library.mvp.VPPresenterImpl
import com.base.library.util.RxHttpUtils
import com.base.module.function.mvp.contract.CommonContract
import rxhttp.wrapper.param.RxHttp

/**
 * 作用: 通用的P层实现
 * 如果你不想自定义P层，可以直接使用这个类
 */
class CommonPresenter(view: CommonContract.View) : VPPresenterImpl<CommonContract.View>(view),
    CommonContract.Presenter {

    override fun requestData(rxHttp: RxHttp<*, *>) {
        rxHttp.asString().compose(transformer())
            .subscribe({
                mView?.responseSuccess(it)
            }, {
                mView?.responseError(msg = RxHttpUtils.getThrowableMessage(it))
            })
    }

}
