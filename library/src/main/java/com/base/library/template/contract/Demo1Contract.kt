package com.zxy.library.framework.template.contract

import com.zxy.library.framework.mvp.BPresenter
import com.zxy.library.framework.mvp.BView

/**
 * 作用: 使用案例,自己定义Contract
 */
interface Demo1Contract {

    interface View : BView {
        fun loginSuccess(request: String?)

        fun loginError(msg: String?)
    }

    interface Presenter : BPresenter {
        fun check(idCard: String)
    }

}