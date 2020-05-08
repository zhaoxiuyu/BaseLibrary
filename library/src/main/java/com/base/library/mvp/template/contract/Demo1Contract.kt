package com.base.library.mvp.template.contract

import com.base.library.mvp.core.VPPresenter
import com.base.library.mvp.core.VPView

/**
 * 作用: 使用案例,自己定义Contract
 */
interface Demo1Contract {

    interface View : VPView {
        fun loginSuccess(request: String?)

        fun loginError(msg: String?)
    }

    interface Presenter : VPPresenter {
        fun check(idCard: String)
    }

}