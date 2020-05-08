package com.base.app.inter

interface CorCallack<T> {

    fun onSuccess(value: T)

    fun onError(t: Throwable) {

    }

}