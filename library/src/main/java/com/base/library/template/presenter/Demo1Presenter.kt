package com.base.library.template.presenter

import com.base.library.base.IDCARD
import com.base.library.base.KEY
import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest
import com.base.library.mvp.BPresenterImpl
import com.blankj.utilcode.util.StringUtils
import com.base.library.template.contract.Demo1Contract

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : BPresenterImpl<Demo1Contract.View>(view), Demo1Contract.Presenter {

    override fun requestSuccess(any: BaseResponse, baseHttpDto: BRequest) {
        super.requestSuccess(any, baseHttpDto)
        when (baseHttpDto.url) {
            IDCARD -> {
//                data class PersonData(var name: String, var age: Int)
//                val loginVo = JsonUtils.toAny(baseResponse.dataInfo.toString(), PersonData::class.java)
            }
        }
    }

    override fun check(idCard: String) {
        if (StringUtils.isEmpty(idCard)) {
            val bRequest = BRequest(IDCARD).apply {
                params = mapOf("key" to KEY, "cardno" to idCard)
            }
            getData(bRequest)
        } else {
            mView?.loginError("身份证不能少于18位")
        }
    }

}
