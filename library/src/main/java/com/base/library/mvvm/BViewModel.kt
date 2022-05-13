package com.base.library.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.ProtectedUnPeekLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
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

    private val _uiChangeState = UnPeekLiveData.Builder<UiChangeState>().create()
    val uiChangeState: ProtectedUnPeekLiveData<UiChangeState> get() = _uiChangeState

    private fun changeState(state: UiChangeState) {
        _uiChangeState.value = state
    }

    fun changeStateLoading(msg: String = "请稍候") {
        changeState(UiChangeState.Loading(msg))
    }

    fun changeStateSuccess(msg: String = "") {
        changeState(UiChangeState.Success(msg))
    }

    fun changeStateFail(msg: String = "") {
        changeState(UiChangeState.Fail(msg))
    }

    fun changeStateMessage(msg: String) {
        changeState(UiChangeState.Message(msg))
    }

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