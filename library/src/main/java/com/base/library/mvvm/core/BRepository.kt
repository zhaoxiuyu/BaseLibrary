package com.base.library.mvvm.core

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BResponse
import com.base.library.mvp.core.SuccessCall
import com.base.library.rxhttp.RxHttpState
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.GsonUtils
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

open class BRepository {

    private var compositeDisposable: CompositeDisposable? = null

    val dialogState = MutableLiveData<RxHttpState>()

    /**
     * 响应数据 BResponse<Student.class>
     */
    fun <T> getResponse(
        request: RxRequest,
        clas: Class<T>,
        liveData: MutableLiveData<BResponse<T>>? = null,
        call: SuccessCall<BResponse<T>>? = null
    ) {
        val disposable = request.getRxHttp().asResponse(clas)
            .compose(transformer(request))
            .subscribe({
                if (it.isSuccess()) {
                    success(request, it, liveData, call)
                } else {
                    error(request, Throwable(it.showMsg()))
                }
            }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 BResponse<MutableList<Student.class>>
     */
    fun <T> getResponseList(
        request: RxRequest, clas: Class<T>,
        liveData: MutableLiveData<BResponse<MutableList<T>>>? = null,
        call: SuccessCall<BResponse<MutableList<T>>>? = null
    ) {
        val disposable = request.getRxHttp().asResponseList(clas)
            .compose(transformer(request))
            .subscribe({
                if (it.isSuccess()) {
                    success(request, it, liveData, call)
                } else {
                    error(request, Throwable(it.showMsg()))
                }
            }, { error(request, it) })
        addDisposable(disposable)
    }


    /**
     * 响应数据 Student.class
     */
    fun <T> getDataClass(
        request: RxRequest, clas: Class<T>,
        liveData: MutableLiveData<T>? = null,
        call: SuccessCall<T>? = null
    ) {
        val disposable = request.getRxHttp().asClass(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, liveData, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    fun <T> getDataList(
        request: RxRequest, clas: Class<T>,
        liveData: MutableLiveData<MutableList<T>>? = null,
        call: SuccessCall<MutableList<T>>? = null
    ) {
        val disposable = request.getRxHttp().asList(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, liveData, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 String
     */
    fun getDataString(
        request: RxRequest,
        liveData: MutableLiveData<String>? = null,
        call: SuccessCall<String>? = null
    ) {
        val disposable = request.getRxHttp().asString()
            .compose(transformer(request))
            .subscribe({ success(request, it, liveData, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 Bitmap
     */
    fun getDataBitmap(
        request: RxRequest,
        liveData: MutableLiveData<Bitmap>? = null,
        call: SuccessCall<Bitmap>? = null
    ) {
        val disposable = request.getRxHttp().asBitmap<Bitmap>()
            .compose(transformer(request))
            .subscribe({ success(request, it, liveData, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    fun <T> getDataMap(
        request: RxRequest,
        clas: Class<T>,
        liveData: MutableLiveData<Map<T, T>>? = null,
        call: SuccessCall<Map<T, T>>? = null
    ) {
        val disposable = request.getRxHttp().asMap(clas)
            .compose(transformer(request))
            .subscribe({ success(request, it, liveData, call) }, { error(request, it) })
        addDisposable(disposable)
    }

    private fun doOnSubscribe(bRequest: RxRequest) {
        Log.d("BRepository", "请求开始")
        Log.d("BRepository", bRequest.print())
        dialogState.value = RxHttpState.Loading(bRequest)
    }

    private fun doFinally() {
        Log.d("BRepository", "请求结束,Completed 不进行回调")
    }

    open fun <T> success(
        request: RxRequest,
        response: T,
        live: MutableLiveData<T>? = null,
        call: SuccessCall<T>? = null
    ) {
        Log.d("BRepository", "请求成功")
        request.msg = "成功"

        // 状态 通知
        dialogState.value = RxHttpState.Success(request)
        // LiveData 通知
        response?.let { live?.value = it }
        // 回调接口
        call?.accept(response)
    }

    /**
     * 组织错误信息
     */
    open fun error(
        request: RxRequest,
        throwable: Throwable?,
        call: SuccessCall<RxRequest>? = null
    ) {
        Log.d("BRepository", "请求失败")

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
        request.msg = msg
        // 状态 通知
        dialogState.value = RxHttpState.Error(request)
        // 回调接口
        call?.accept(request)

        throwable?.printStackTrace()
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
     * 页面销毁,切断所有订阅事件
     */
    fun cleared() {
        Log.d("BRepository", "cleared")
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

}