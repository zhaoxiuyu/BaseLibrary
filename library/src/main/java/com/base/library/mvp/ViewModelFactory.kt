package com.base.library.mvp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 创建ViewModel的工厂方法
 */
class ViewModelFactory(var mView: BView, var cla: Class<*>) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cons = cla.getConstructor(BView::class.java)
        return cons.newInstance(mView) as T
//        return RegisterViewModel(mView) as T
    }

}