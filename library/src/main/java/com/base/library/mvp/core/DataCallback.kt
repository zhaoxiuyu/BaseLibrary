package com.base.library.mvp.core

import io.reactivex.rxjava3.functions.Consumer

open interface DataCallback<T> : Consumer<T> {

    override fun accept(t: T)

}