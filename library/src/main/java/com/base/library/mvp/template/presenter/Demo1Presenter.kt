package com.base.library.mvp.template.presenter

import com.base.library.entitys.BRequest
import com.base.library.entitys.BaseResponse
import com.base.library.http.HttpConstant
import com.base.library.mvp.core.VPPresenterImpl
import com.base.library.mvp.template.contract.Demo1Contract
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : VPPresenterImpl<Demo1Contract.View>(view),
    Demo1Contract.Presenter {

    override fun requestSuccess(response: BaseResponse, bRequest: BRequest) {
        super.requestSuccess(response, bRequest)
        LogUtils.d("登录状态 : ${response.code}")
    }

    override fun check(idCard: String) {
        if (StringUtils.isEmpty(idCard)) {
            val bRequest = BRequest(HttpConstant.IDCARD).apply {
                params = mapOf("key" to HttpConstant.KEY, "cardno" to idCard)
            }
            getData(bRequest)
        } else {
            mView?.loginError("身份证不能少于18位")
        }
    }

}
