package com.zxy.library.framework.mvp

import com.base.library.entitys.BaseResponse

interface BaseView : BView {

    fun bindData(baseResponse: BaseResponse)

    fun bindError(string: String)

}