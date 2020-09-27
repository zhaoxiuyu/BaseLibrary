package com.base.library.mvvm.core

import android.util.Log
import androidx.lifecycle.Observer
import com.base.library.base.BActivity
import com.base.library.rxhttp.RxRequest
import rxhttp.wrapper.entity.Progress

/**
 * MVVM 模式的基础 Activity
 */
abstract class VMActivity : BActivity(), OnHandleCallback {

    // 旧的使用方式
//abstract class VMActivity<VM : VMViewModel> : BActivity() {
//    protected var vm: VM? = null

    /**
     * 默认走这个里面的提示框流程和样式
     * 实现类可以重写这个方法进行定制
     */
    override fun initView() {
        dialogState()
    }

    open fun dialogState() {
        vm?.dialogState?.observe(this, Observer { it.handler(this) })
    }

    override fun onLoading(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onLoading \n" + mRequest.toBasicString())
        if (!mRequest.silence) {
            showLoading(null, mRequest.msg)
        }
    }

    override fun onSuccess(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onSuccess \n" + mRequest.toBasicString())
        handleDialog(mRequest)
    }

    override fun onError(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onError \n" + mRequest.toBasicString())
        handleDialog(mRequest)
    }

    override fun onCompleted() {
        Log.v("OnHandleCallback", "onCompleted")
    }

    override fun onProgress(progress: Progress?) {
        Log.v("OnHandleCallback", "onProgress")
    }

    // 成功失败的回调，处理Dialog的逻辑是一样的
    private fun handleDialog(mRequest: RxRequest) {
        if (!mRequest.silence) {
            // 不是静默加载 才调用关闭Dialog
            dismissDialog()
        }

        // 成功失败 是否弹出提示
        if (mRequest.finally) {
            // 点击确定是否销毁页面
            val df = getDismissFinish(mRequest.isFinish)
            showDialog(content = mRequest.msg, confirmLi = df)
        } else if (!mRequest.finally && mRequest.directFinish) {
            // 不弹出提示框 并且 直接销毁页面
            finish()
        }
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
//            val df = getDismissFinish(isFinish) // 点击确定 是否销毁当前页面
//            showDialog(content = message, confirmLi = df)
//        }
//
//        override fun onCompleted() {
//            Log.v("OnCallback", "onCompleted")
//            dismissDialog(false)
//        }
//
//        override fun onProgress(progress: Progress?) {
//            Log.v("OnCallback", "onProgress")
//        }
//    }

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
