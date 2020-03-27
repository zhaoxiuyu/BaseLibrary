package com.base.app.module.common.presenter

import com.base.app.module.common.contract.LoginContract
import com.base.library.entitys.BaseResponse
import com.base.library.base.BRequest
import com.base.library.mvp.VPPresenterImpl
import com.blankj.utilcode.util.LogUtils

class LoginPresenter(view: LoginContract.View) : VPPresenterImpl<LoginContract.View>(view),
    LoginContract.Presenter {

    override fun login(idCard: String) {
        val bRequest = BRequest("https://www.wanandroid.com/banner/json")
        getData(bRequest)
        LogUtils.d(bRequest.tag)
    }

    override fun requestSuccess(response: BaseResponse, bRequest: BRequest) {
        super.requestSuccess(response, bRequest)
        if (response.errorCode == 0) {
            mView?.loginSuccess("成功")
        } else {
            mView?.loginError("错误")
        }
    }

//    override fun requestSuccess(body: String, baseHttpDto: BRequest) {
//        super.requestSuccess(body, baseHttpDto)
//
//        Observable.just(body)
//            .map {
//                JsonUtils.toAny(body, BaseResponse::class.java)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
//            .subscribe {
//                if (it.errorCode == "0") {
//                    mView?.loginSuccess("成功")
//                } else {
//                    mView?.loginError("错误")
//                }
//            }
//    }

}