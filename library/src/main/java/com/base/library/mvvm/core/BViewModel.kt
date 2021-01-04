package com.base.library.mvvm.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.rxLifeScope
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class BViewModel : ViewModel() {

    private val mRepository by lazy { BRepository() }

    fun getRepository() = mRepository
    fun getState() = mRepository.dialogState

    /**
     * 获取缓存
     */
    val cacheLiveData by lazy { MutableLiveData<String>() }
    fun getCache(key: String, consumer: Consumer<String>? = null) {
        rxLifeScope.launch({
            val cache = withContext(Dispatchers.IO) { CacheDiskStaticUtils.getString(key, "") }
            cacheLiveData.value = cache
            consumer?.accept(cache)
        }, {
            LogUtils.e("获取缓存 $key 出错了,${it.message}")
        })
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
        mRepository.cleared()
    }

}