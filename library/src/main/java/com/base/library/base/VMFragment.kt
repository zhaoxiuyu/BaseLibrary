package com.base.library.base

import com.lzy.okgo.model.Progress

abstract class VMFragment : BFragment() {

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
    abstract inner class OnCallback<T> : BResponse.OnHandleCallback<T> {
        override fun onLoading(msg: String) {
            showLoading(null, msg)
        }

        override fun onSuccess(msg: String, data: T?, isFinish: Boolean) {
        }

        override fun onError(error: Throwable?, isFinish: Boolean) {
            showDialog(content = "${error?.message}")
        }

        override fun onFailure(msg: String, isFinish: Boolean) {
            showDialog(content = msg)
        }

        override fun onCompleted() {
            dismissDialog(false)
        }

        override fun onProgress(progress: Progress?) {
        }
    }

}