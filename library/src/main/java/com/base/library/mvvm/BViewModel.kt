package com.base.library.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.library.event.EventState
import com.base.library.event.SingleLiveEvent
import com.base.library.util.RxHttpUtils
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
open class BViewModel : ViewModel(), OnHandleCallback {

    /**
     * 操作状态通知
     */
    private val uiChangeLiveData by lazy { SingleLiveEvent<EventState>() }

    //    private val uiChangeLiveData by lazy { MutableLiveData<EventState>() }
    fun getUIChangeLiveData() = uiChangeLiveData

    private var mDisposables: CompositeDisposable? = null

    override fun loadingEvent(method: String, msg: String) {
        getUIChangeLiveData().value =
            EventState.getStateEntity(method, msg, EventState.DIALOGLOADING)
    }

    override fun messageEvent(method: String, msg: String, finish: Boolean) {
        getUIChangeLiveData().value =
            EventState.getStateEntity(method, msg, EventState.DIALOGMESSAGE, finish)
    }

    override fun dismissEvent(method: String) {
        getUIChangeLiveData().value =
            EventState.getStateEntity(method, state = EventState.DIALOGDISMISS)
    }

    override fun finishEvent() {
    }

    override fun startActivityEvent() {
    }

    override fun otherEvent(content: String) {
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    protected fun <T> transformerThread(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    protected fun <T> transformerEvent(method: String = ""): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnSubscribe { loadingEvent(method) }.doFinally { dismissEvent(method) }
        }
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    protected fun <T> transformer(method: String = ""): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.compose(transformerThread()).compose(transformerEvent(method))
        }
    }

    /**
     * 需要加载框和取消加载框的可以使用这个方法
     */
    fun viewModelScopeLoadDisMess(method: String = "", block: suspend CoroutineScope.() -> Unit) {
        viewModelScope(block,
            { messageEvent(method, RxHttpUtils.getThrowableMessage(it)) },
            { loadingEvent(method) },
            { dismissEvent(method) })
    }

    fun viewModelScopeLoadDis(method: String = "", block: suspend CoroutineScope.() -> Unit) {
        viewModelScope(block, null,
            { loadingEvent(method) },
            { dismissEvent(method) })
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