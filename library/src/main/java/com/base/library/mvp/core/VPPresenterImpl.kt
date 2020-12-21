package com.base.library.mvp.core

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.base.library.entitys.BResponse
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 作用：P层的基础实现类
 * 实现了网络请求 返回 取消等处理
 */
open class VPPresenterImpl<T : VPView?>(var mView: T?) : VPPresenter, VPCallback {

    private var compositeDisposable: CompositeDisposable? = null

    /**
     * 响应数据 BResponse<Student.class>
     */
    override fun <T> getResponse(
        bRequest: RxRequest,
        clas: Class<T>,
        call: SuccessCall<BResponse<T>>?
    ) {
        val disposable = bRequest.getRxHttp().asResponse(clas)
            .compose(transformer(bRequest.showLoading))
            .subscribe({
                if (it.isSuccess()) {
                    success(bRequest, it, call)
                } else {
                    error(bRequest, Throwable(it.showMsg()))
                }
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 BResponse<MutableList<Student.class>>
     */
    override fun <T> getResponseList(
        bRequest: RxRequest,
        clas: Class<T>,
        call: SuccessCall<BResponse<MutableList<T>>>?
    ) {
        val disposable = bRequest.getRxHttp().asResponseList(clas)
            .compose(transformer(bRequest.showLoading))
            .subscribe({
                if (it.isSuccess()) {
                    success(bRequest, it, call)
                } else {
                    error(bRequest, Throwable(it.showMsg()))
                }
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 Student.class
     */
    override fun <T> getDataClass(bRequest: RxRequest, clas: Class<T>, call: SuccessCall<T>?) {
        val disposable = bRequest.getRxHttp().asClass(clas)
            .compose(transformer(bRequest.showLoading))
            .subscribe({ success(bRequest, it, call) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    override fun <T> getDataList(
        bRequest: RxRequest,
        clas: Class<T>,
        call: SuccessCall<MutableList<T>>?
    ) {
        val disposable = bRequest.getRxHttp().asList(clas)
            .compose(transformer(bRequest.showLoading))
            .subscribe({ success(bRequest, it, call) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 String
     */
    override fun getDataString(bRequest: RxRequest, call: SuccessCall<String>?) {
        val disposable = bRequest.getRxHttp().asString()
            .compose(transformer(bRequest.showLoading))
            .subscribe({ success(bRequest, it, call) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 Bitmap
     */
    override fun getDataBitmap(bRequest: RxRequest, call: SuccessCall<Bitmap>?) {
        val disposable = bRequest.getRxHttp().asBitmap<Bitmap>()
            .compose(transformer(bRequest.showLoading))
            .subscribe({ success(bRequest, it, call) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    override fun <T> getDataMap(
        bRequest: RxRequest,
        clas: Class<T>,
        call: SuccessCall<Map<T, T>>?
    ) {
        val disposable = bRequest.getRxHttp().asMap(clas)
            .compose(transformer(bRequest.showLoading))
            .subscribe({ success(bRequest, it, call) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    override fun doOnSubscribe(silence: Boolean) {
        Log.d("VPPresenterImpl", "请求开始 是否静默加载 $silence")
        if (!silence) {
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

        val msg = if (throwable is UnknownHostException) {
            // 通过 OkHttpClient 设置的超时 引发的异常
            if (NetworkUtils.isConnected()) "网络连接不可用" else "当前无网络"
        } else if (throwable is SocketTimeoutException || throwable is TimeoutException) {
            // 对单个请求调用 timeout 方法引发的超时异常
            "连接超时,请稍后再试"
        } else if (throwable is ConnectException) {
            "网络不给力,请稍候重试"
        } else if (throwable is HttpStatusCodeException) {
            val result = throwable.result
            try {
                val bResponse = GsonUtils.getGson().fromJson(result, BResponse::class.java)
                bResponse.message ?: "请求异常"
            } catch (e: Exception) {
                throwable.message ?: "请求异常"
            }
        } else if (throwable is JsonSyntaxException) {
            //  请求成功,但Json语法异常,导致解析失败
            "数据解析失败,请稍后再试"
        } else if (throwable is ParseException) {
            //  ParseException异常表明请求成功，但是数据不正确
            throwable.message ?: throwable.localizedMessage ?: ""
        } else {
            throwable?.message ?: "出现异常"
        }
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
    private fun <T> transformer(silence: Boolean): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { doOnSubscribe(silence) }
                .doFinally { doFinally() }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("VPPresenterImpl", "onDestroy")
        compositeDisposable?.dispose()
    }

}