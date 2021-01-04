package com.base.library.mvp.core

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.base.library.entitys.BResponse
import com.base.library.interfaces.MyLifecycle
import com.base.library.rxhttp.RxRequest
import com.base.library.util.OtherUtils
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import com.rxlife.coroutine.RxLifeScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 作用：P层的基础实现类
 * 实现了网络请求 返回 取消等处理
 */
open class VPPresenterImpl<T : VPView?>(var mView: T?) : VPPresenter, VPCallback, MyLifecycle {

    private var compositeDisposable: CompositeDisposable? = null

    /**
     * 响应数据 BResponse<Student.class>
     */
    override fun <T> getResponse(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<BResponse<T>>?
    ) {
        val disposable = request.getRxHttp().asResponse(clas)
            .compose(transformer(request))
            .subscribe({
                if (it.isSuccess()) {
                    success(request, it, call)
                } else {
                    error(request, Throwable(it.showMsg()))
                }
            }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 BResponse<MutableList<Student.class>>
     */
    override fun <T> getResponseList(
        request: RxRequest, clas: Class<T>,
        call: SuccessCall<BResponse<MutableList<T>>>?
    ) {
        val disposable = request.getRxHttp().asResponseList(clas)
            .compose(transformer(request))
            .subscribe({
                if (it.isSuccess()) {
                    success(request, it, call)
                } else {
                    error(request, Throwable(it.showMsg()))
                }
            }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 Student.class
     */
    override fun <T> getDataClass(
        request: RxRequest, clas: Class<T>,
        call: SuccessCall<T>?
    ) {
        val disposable = request.getRxHttp().asClass(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    override fun <T> getDataList(
        request: RxRequest, clas: Class<T>,
        call: SuccessCall<MutableList<T>>?
    ) {
        val disposable = request.getRxHttp().asList(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 String
     */
    override fun getDataString(
        request: RxRequest,
        call: SuccessCall<String>?
    ) {
        val disposable = request.getRxHttp().asString()
            .compose(transformer(request))
            .subscribe({ success(request, it, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 Bitmap
     */
    override fun getDataBitmap(request: RxRequest, call: SuccessCall<Bitmap>?) {
        val disposable = request.getRxHttp().asBitmap<Bitmap>()
            .compose(transformer(request))
            .subscribe({ success(request, it, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    override fun <T> getDataMap(
        request: RxRequest,
        clas: Class<T>,
        call: SuccessCall<Map<T, T>>?
    ) {
        val disposable = request.getRxHttp().asMap(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 获取缓存
     */
    private var getCacheJob: Job? = null
    fun getCache(key: String, consumer: Consumer<String>) {
        getCacheJob = RxLifeScope().launch({
            val cache = withContext(Dispatchers.IO) { CacheDiskStaticUtils.getString(key, "") }
            consumer.accept(cache)
        }, {
            LogUtils.e("获取缓存 $key 出错了,${it.message}")
        })
    }

    /**
     * 保存缓存
     */
    private var putCacheJob: Job? = null
    fun putCache(key: String, content: String, time: Int = -1) {
        putCacheJob = RxLifeScope().launch({
            launch(Dispatchers.IO) { CacheDiskStaticUtils.put(key, content, time) }
        }, {
            LogUtils.e("添加缓存 $key 出错了,${it.message}")
        })
    }

    override fun doOnSubscribe(request: RxRequest) {
        Log.d("VPPresenterImpl", "请求开始")
        Log.d("VPPresenterImpl", request.print())

        if (request.showLoading) {
            mView?.showDialog()
        }
    }

    override fun doFinally() {
        Log.d("VPPresenterImpl", "请求结束")
    }

    override fun <T> success(req: RxRequest, res: T, call: SuccessCall<T>?) {
        Log.d("VPPresenterImpl", "请求成功")
        mView?.disDialog()
        call?.accept(res)
    }

    override fun error(bRequest: RxRequest, throwable: Throwable?) {
        mView?.disDialog()

        val msg = OtherUtils.getThrowableMessage(throwable)
        LogUtils.e(msg)

        /**
         * 不属于静默加载才弹窗
         */
        if (bRequest.showLoading) {
            val fl = if (bRequest.showFail) mView?.getDismissFinishListener() else null
            mView?.showDialog(title = "异常提示", content = msg, confirmLi = fl)
        }

        throwable?.printStackTrace()
    }

    override fun other(content: String, behavior: String, level: String) {
        mView?.other(content, behavior, level)
    }

    /**
     * 添加一个订阅事件
     */
    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    private fun <T> transformer(bRequest: RxRequest): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { doOnSubscribe(bRequest) }
                .doFinally { doFinally() }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("VPPresenterImpl", "onDestroy")
        compositeDisposable?.dispose()
        compositeDisposable = null

        getCacheJob?.cancel()
        putCacheJob?.cancel()
    }

}