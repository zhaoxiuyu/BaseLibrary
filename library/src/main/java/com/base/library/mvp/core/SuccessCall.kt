package com.base.library.mvp.core

import io.reactivex.rxjava3.functions.Consumer

open interface SuccessCall<T> : Consumer<T> {

    override fun accept(bResponse: T)

}