package com.base.app.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.base.library.mvp.BView

/**
 * 创建ViewModel的工厂方法
 */
class FactoryViewModel(var mView: BView, var cla: Class<*>) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cons = cla.getConstructor(BView::class.java)
        return cons.newInstance(mView) as T
//        return RegisterViewModel(mView) as T
    }

}