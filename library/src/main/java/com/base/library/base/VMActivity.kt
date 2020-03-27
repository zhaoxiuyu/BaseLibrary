package com.base.library.base

import androidx.lifecycle.ViewModelProvider
import com.base.library.mvvm.VMViewModel
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.model.Progress
import java.lang.reflect.ParameterizedType

/**
 * MVVM 模式的基础 Activity
 */
abstract class VMActivity<VM : VMViewModel> : BActivity() {

    var vm: VM? = null

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
    abstract inner class OnCallback<T> : BResponse.OnHandleCallback<T> {
        override fun onLoading(msg: String) {
            LogUtils.d("onLoading")
            showLoading(null, msg)
        }

        override fun onSuccess(msg: String, data: T?, isFinish: Boolean) {
            LogUtils.d("onSuccess")
        }

        override fun onError(error: Throwable?, isFinish: Boolean) {
            LogUtils.d("onError")
            onFailure("${error?.message}", isFinish)
        }

        override fun onFailure(msg: String, isFinish: Boolean) {
            LogUtils.d("onFailure")
            val df = if (isFinish) getDismissFinish() else null // 点击确定 是否销毁当前页面
            showDialog(content = msg, confirmLi = df)
        }

        override fun onCompleted() {
            LogUtils.d("onCompleted")
            dismissDialog(false)
        }

        override fun onProgress(progress: Progress?) {
            LogUtils.d("onProgress")
        }
    }

    fun createViewModel() {
        val type = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<*>
        } else {
            VMViewModel::class.java
        }
//        vm = ViewModelProvider(this).get(modelClass)
    }

//    public void createViewModel() {
//        if (mViewModel == null) {
//            Class modelClass;
//            Type type = getClass().getGenericSuperclass();
//            if (type instanceof ParameterizedType) {
//                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
//            } else {
//                //如果没有指定泛型参数，则默认使用BaseViewModel
//                modelClass = BaseViewModel.class;
//            }
//            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
//        }
//    }

}