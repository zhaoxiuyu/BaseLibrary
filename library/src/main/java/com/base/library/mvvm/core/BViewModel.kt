package com.base.library.mvvm.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.rxLifeScope
import com.base.library.rxhttp.ResponseState
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class BViewModel : ViewModel() {

    // 一个ViewModel只有一个 网络请求弹窗状态 ，所有的ViewModel实现类和数据仓库公用这一个
    // 所以在getState方法里面会对其进行赋值
    private val requestState = MutableLiveData<ResponseState>()

    open fun getRepository(): BRepository? = null

    open fun getState(): MutableLiveData<ResponseState>? {
        getRepository()?.dialogState = requestState
        return requestState
    }

    fun sendState(mResponseState: ResponseState) {
        requestState.value = mResponseState
    }

    /**
     * 添加缓存
     */
    fun putCache(key: String, content: String, time: Int = -1) {
        rxLifeScope.launch({
            launch(Dispatchers.IO) { CacheDiskStaticUtils.put(key, content, time) }
        }, {
            LogUtils.e("添加缓存 $key 出错了")
        })
    }

    /**
     * 页面销毁,切断所有订阅事件
     */
    override fun onCleared() {
        super.onCleared()
        getRepository()?.cleared()
    }

}