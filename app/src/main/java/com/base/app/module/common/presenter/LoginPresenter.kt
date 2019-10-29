package com.base.app.module.common.presenter

import com.base.app.module.common.contract.LoginContract
import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest
import com.base.library.mvp.BPresenterImpl
import com.base.library.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(view: LoginContract.View) : BPresenterImpl<LoginContract.View>(view),
    LoginContract.Presenter {

    override fun login(idCard: String) {
        val bRequest = BRequest("https://www.wanandroid.com/banner/json")
        getData(bRequest)

        LogUtils.d(bRequest.tag)


    }

    override fun requestSuccess(body: String, baseHttpDto: BRequest) {
        super.requestSuccess(body, baseHttpDto)

        Observable.just(body)
            .map {
                JsonUtils.toAny(body, BaseResponse::class.java)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
            .subscribe {
                if (it.errorCode == "0") {
                    mView?.loginSuccess("成功")
                } else {
                    mView?.loginError("错误")
                }
            }
    }

}