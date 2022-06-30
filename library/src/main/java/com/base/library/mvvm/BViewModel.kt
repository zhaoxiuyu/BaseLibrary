package com.base.library.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class BViewModel : ViewModel() {

    private var mDisposables: CompositeDisposable? = null

    /**
     * 变换 IO线程 -> Main线程
     */
    protected fun <T> transformerThread(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun viewModelScope(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                onStart?.invoke()
                block()
            } catch (e: Exception) {
                onError?.invoke(e)
                e.printStackTrace()
            } finally {
                onFinally?.invoke()
            }
        }
    }

    /**
     * 将 Disposable 添加进容器
     */
    protected fun addDisposable(disposable: Disposable) {
        if (mDisposables == null) {
            mDisposables = CompositeDisposable()
        }
        mDisposables?.add(disposable)
    }

    /**
     * 中断所有的 Disposable,中断之后 mDisposables 将不可用,是一次性的
     */
    private fun dispose() {
        mDisposables?.dispose()
        mDisposables = null
    }

    /**
     * 页面销毁
     */
    override fun onCleared() {
        super.onCleared()
        dispose() // 中断 RxJava 管道
    }

}