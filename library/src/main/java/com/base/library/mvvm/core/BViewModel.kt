package com.base.library.mvvm.core

import androidx.lifecycle.ViewModel

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class BViewModel : ViewModel() {

    private val mRepository by lazy { BRepository() }

    fun getRepository() = mRepository
    fun getState() = mRepository.dialogState

    /**
     * 页面销毁,切断所有订阅事件
     */
    override fun onCleared() {
        super.onCleared()
        mRepository.cleared()
    }

}