package com.base.library.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.base.library.mvvm.VMViewModel
import com.lzy.okgo.model.Progress
import java.lang.reflect.ParameterizedType

abstract class VMFragment<VM : VMViewModel> : BFragment() {

    protected var vm: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
    abstract inner class OnCallback<T> : BResponse.OnHandleCallback<T> {
        override fun onLoading(msg: String) {
            Log.v("OnCallback", "onLoading")
            showLoading(null, msg)
        }

        override fun onSuccess(msg: String, data: T?, isFinish: Boolean) {
            Log.v("OnCallback", "onSuccess")
        }

        override fun onError(error: Throwable?, isFinish: Boolean) {
            Log.v("OnCallback", "onError")
            showDialog(content = "${error?.message}")
        }

        override fun onFailure(msg: String, isFinish: Boolean) {
            Log.v("OnCallback", "onFailure")
            showDialog(content = msg)
        }

        override fun onCompleted() {
            Log.v("OnCallback", "onCompleted")
            dismissDialog(false)
        }

        override fun onProgress(progress: Progress?) {
            Log.v("OnCallback", "onProgress")
        }
    }

    private fun createViewModel() {
        vm ?: let {
            val type = javaClass.genericSuperclass
            val modelClass = if (type is ParameterizedType) {
                type.actualTypeArguments[0] as Class<*>
            } else {
                VMViewModel::class.java
            }
            vm = ViewModelProvider(this).get(modelClass as Class<VMViewModel>) as VM
        }
    }

}