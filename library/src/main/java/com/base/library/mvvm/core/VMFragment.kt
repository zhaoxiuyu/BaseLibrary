package com.base.library.mvvm.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.library.base.BFragment
import com.base.library.entitys.BResponse
import com.lzy.okgo.model.Progress
import java.lang.reflect.ParameterizedType

abstract class VMFragment<VM : VMViewModel> : BFragment() {

    protected var vm: VM? = null
    private var mSharedViewModel: SharedViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
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
        vm?.stateLiveData?.observe(this, Observer { stateLiveData(it) })
        mSharedViewModel =
            ViewModelProvider(activity as AppCompatActivity).get(SharedViewModel::class.java)
    }

    /**
     * 默认走这个里面的提示框流程和样式
     * 实现类可以重写这个方法进行定制
     */
    open fun stateLiveData(bResponse: BResponse<Any>) {
        bResponse.handler(object : OnCallback<Any>() {})
    }

    /**
     * 通过这个可以实现Activity/Fragment页面之间的通信
     */
    fun getSharedViewModel(): SharedViewModel? = mSharedViewModel

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
    abstract inner class OnCallback<T> : OnHandleCallback {
        override fun onLoading(msg: String) {
            Log.v("OnCallback", "onLoading")
            showLoading(null, msg)
        }

        override fun onSuccess(msg: String, data: Any?, isFinish: Boolean) {
            Log.v("OnCallback", "onSuccess")
        }

        override fun onError(error: Throwable?, message: String, isFinish: Boolean) {
            Log.v("OnCallback", "onError")
            showDialog(content = message)
        }

        override fun onCompleted() {
            Log.v("OnCallback", "onCompleted")
            dismissDialog(false)
        }

        override fun onProgress(progress: Progress?) {
            Log.v("OnCallback", "onProgress")
        }
    }

}