package com.base.library.mvp

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.base.library.data.http.HttpDataSourceImpl
import com.base.library.data.local.LocalDataSourceImpl
import com.base.library.mvvm.OnHandleCallback
import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 作用：P层的基础实现类
 * 实现了网络请求 返回 取消等处理
 */
open class VPPresenterImpl<T : OnHandleCallback?>(var mView: T?) : VPPresenter {
//    open class VPPresenterImpl<T : VPView?>(var mView: T?) : VPPresenter, VPCallback, MyLifecycle {

    val mHttpData = HttpDataSourceImpl.getInstance
    private val mLocalData = LocalDataSourceImpl.getInstance

    private var compositeDisposable: CompositeDisposable? = null

    /**
     * 获取缓存
     */
    fun getCache(key: String, consumer: Consumer<String>) {
        addDisposable(
            Observable.create<String> { mLocalData.getCache(key) }.compose(transformerThread())
                .subscribe({ consumer.accept(it) }, {
                    consumer.accept(null)
                    LogUtils.e("获取缓存 $key 出错了,${it.message}")
                })
        )
    }

    /**
     * 保存缓存
     */
    fun putCache(key: String, content: String, time: Int = -1, consumer: Consumer<Boolean>) {
        addDisposable(
            Observable.create<String> { mLocalData.putCache(key, content, time) }
                .compose(transformerThread()).subscribe({ consumer.accept(true) }, {
                    consumer.accept(false)
                    LogUtils.e("添加缓存 $key 出错了,${it.message}")
                })
        )
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
            it.doOnSubscribe { mView?.loadingEvent(method) }
                .doFinally { mView?.dismissEvent(method) }
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
     * 添加一个订阅事件
     */
    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("VPPresenterImpl", "onDestroy")
        compositeDisposable?.dispose()
        compositeDisposable = null
        mView = null
    }

}