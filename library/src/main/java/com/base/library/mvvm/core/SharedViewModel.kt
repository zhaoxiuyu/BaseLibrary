package com.base.library.mvvm.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 用来在 Activity/Fragment 页面之间的通信
 */
class SharedViewModel : ViewModel() {

    private val eventLiveData = MutableLiveData<Triple<String, Boolean, Any>>()

    fun getEventLiveData(): MutableLiveData<Triple<String, Boolean, Any>> = eventLiveData

}