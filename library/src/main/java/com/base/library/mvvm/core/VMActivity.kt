package com.base.library.mvvm.core

import android.util.Log
import com.base.library.base.BActivity
import com.base.library.entitys.BResponse
import com.lzy.okgo.model.Progress

/**
 * MVVM 模式的基础 Activity
 */
abstract class VMActivity : BActivity() {

    // 旧的使用方式
//abstract class VMActivity<VM : VMViewModel> : BActivity() {
//    protected var vm: VM? = null

    /**
     * 默认走这个里面的提示框流程和样式
     * 实现类可以重写这个方法进行定制
     */
    open fun stateLiveData(bResponse: BResponse<Any>) {
        bResponse.handler(object : OnCallback<Any>() {})
    }

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
            val df = if (isFinish) getDismissFinish() else null // 点击确定 是否销毁当前页面
            showDialog(content = message, confirmLi = df)
        }

        override fun onCompleted() {
            Log.v("OnCallback", "onCompleted")
            dismissDialog(false)
        }

        override fun onProgress(progress: Progress?) {
            Log.v("OnCallback", "onProgress")
        }
    }

    /**
     * 旧的使用方式
     */

//    最原始的初始化ViewModel方式
//    private val vm by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        createViewModel()
//        super.onCreate(savedInstanceState)
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
//        mSharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
//    }

}
