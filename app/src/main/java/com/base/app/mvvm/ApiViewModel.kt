package com.base.app.mvvm

import com.base.library.http.BRequest
import com.base.library.mvp.BCallback
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request

open class ApiViewModel : BaseViewModel() {

    fun getData(http: BRequest) {
        http.getOkGo().execute(object : BCallback(null, true) {

        })
    }


}