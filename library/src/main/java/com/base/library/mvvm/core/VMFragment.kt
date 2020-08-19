package com.base.library.mvvm.core

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.base.library.base.BFragment
import rxhttp.wrapper.entity.Progress

abstract class VMFragment : BFragment(), OnHandleCallback {

    // 旧的使用方式
//abstract class VMFragment<VM : VMViewModel> : BFragment() {
//    protected var vm: VM? = null

    override fun initView(bundle: Bundle?) {
        dialogState()
    }

    /**
     * 默认走这个里面的提示框流程和样式
     * 实现类可以重写这个方法进行定制
     */
    open fun dialogState() {
        vm?.dialogState?.observe(this, Observer { it.handler(this) })
//        vm?.dialogState?.observe(this, Observer { it.handler(object : OnCallback() {}) })
    }

    override fun onLoading(msg: String, isSilence: Boolean) {
        Log.v("OnCallback", "onLoading - msg=$msg - isSilence=$isSilence")
        if (!isSilence) {
            showLoading(null, msg)
        }
    }

    override fun onSuccess(msg: String, url: String?, isFinish: Boolean, isSilence: Boolean) {
        val log = "onSuccess - msg=$msg - url=$url - isFinish=$isFinish - isSilence=$isSilence"
        Log.v("OnCallback", log)
        if (!isSilence) {
            dismissDialog(false)
        }
    }

    override fun onError(msg: String, url: String?, isFinish: Boolean, isSilence: Boolean) {
        val log = "onError - msg=$msg - url=$url - isFinish=$isFinish - isSilence=$isSilence"
        Log.v("OnCallback", log)
        if (!isSilence) {
            val df = getDismissFinish(isFinish = isFinish) // 点击确定 是否销毁当前页面
            showDialog(content = msg, confirmLi = df)
        }
    }

    override fun onCompleted(msg: String, url: String?) {
        Log.v("OnCallback", "onCompleted - msg=$msg - url=$url")
    }

    override fun onProgress(progress: Progress?) {
        Log.v("OnCallback", "onProgress")
    }

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
//    abstract inner class OnCallback : OnHandleCallback {
//        override fun onLoading(msg: String) {
//            Log.v("OnCallback", "onLoading")
//            showLoading(null, msg)
//        }
//
//        override fun onSuccess(msg: String, isFinish: Boolean) {
//            Log.v("OnCallback", "onSuccess")
//        }
//
//        override fun onError(error: Throwable?, message: String, isFinish: Boolean) {
//            Log.v("OnCallback", "onError")
//            showDialog(content = message)
//        }
//
//        override fun onCompleted() {
//            Log.v("OnCallback", "onCompleted")
//            dismissDialog(false)
//        }
//
//        override fun onProgress(progress: rxhttp.wrapper.entity.Progress?) {
//            Log.v("OnCallback", "onProgress")
//        }
//    }

//    最原始的初始化ViewModel方式
//    private val vm by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        createViewModel()
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

//    根据传入的泛型进行初始化ViewModel
//    private fun createViewModel() {
//        vm ?: let {
//            val type = javaClass.genericSuperclass
//            val modelClass = if (type is ParameterizedType) {
//                type.actualTypeArguments[0] as Class<*>
//            } else {
//                VMViewModel::class.java
//            }
//            vm = ViewModelProvider(this).get(modelClass as Class<VMViewModel>) as VM
//        }
//        vm?.stateLiveData?.observe(this, Observer { stateLiveData(it) })
//        mSharedViewModel =
//            ViewModelProvider(activity as AppCompatActivity).get(SharedViewModel::class.java)
//    }

}