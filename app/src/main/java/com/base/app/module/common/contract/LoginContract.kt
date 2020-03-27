package com.base.app.module.common.contract

import com.base.library.mvp.VPPresenter
import com.base.library.mvp.VPView

interface LoginContract {

    interface View : VPView {
        fun loginSuccess(request: String?)

        fun loginError(msg: String?)
    }

    interface Presenter : VPPresenter {
        fun login(idCard: String)
    }

}