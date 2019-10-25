package com.base.app.module.common.contract

import com.base.library.mvp.BPresenter
import com.base.library.mvp.BView

interface LoginContract {

    interface View : BView {
        fun loginSuccess(request: String?)

        fun loginError(msg: String?)
    }

    interface Presenter : BPresenter {
        fun login(idCard: String)
    }

}