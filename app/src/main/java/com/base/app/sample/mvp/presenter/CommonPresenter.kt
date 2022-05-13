package com.base.app.sample.mvp.presenter

import com.base.app.sample.mvi.repository.Demo5Repository
import com.base.app.sample.mvp.contract.CommonContract
import com.base.library.mvp.VPPresenterImpl
import io.reactivex.rxjava3.core.Observable

/**
 * 作用: 通用的P层实现
 * 如果你不想自定义P层，可以直接使用这个类
 */
class CommonPresenter(view: CommonContract.View) : VPPresenterImpl<CommonContract.View>(view),
    CommonContract.Presenter {
    private val mRepository by lazy { Demo5Repository() }

    override fun requestData(tips: String) {
        addDisposable(
            Observable.create<String> {
                mRepository.getCache("")
            }.compose(transformer())
                .subscribe({
                    mView?.responseSuccess(it)
                }, {
                    mView?.responseError(msg = it.message ?: "")
                })
        )
    }
}
