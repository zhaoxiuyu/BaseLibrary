package com.base.library.mvp

interface BaseView : BView {

    fun bindData(body: Any)

    fun bindError(string: String)

}