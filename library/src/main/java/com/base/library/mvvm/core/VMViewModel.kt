package com.base.library.mvvm.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.library.entitys.BResponse
import com.base.library.mvp.core.SuccessCall
import com.base.library.rxhttp.RxHttpState
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class VMViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val dialogState = MutableLiveData<RxHttpState>()

    /**
     * ----------------------------- 响应数据 直接分发给 LiveData ----------------------------------------------------
     */
    fun <T> getData(bRequest: RxRequest, liveData: MutableLiveData<BResponse<T>>, clas: Class<T>) {
        val disposable = bRequest.getRxHttp().asResponse(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({ success(bRequest, liveData, it) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun <T> getDatas(
        bRequest: RxRequest,
        liveData: MutableLiveData<BResponse<MutableList<T>>>,
        clas: Class<T>
    ) {
        val disposable = bRequest.getRxHttp().asResponseList(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({ success(bRequest, liveData, it) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    open fun <T> success(req: RxRequest, live: MutableLiveData<BResponse<T>>, res: BResponse<T>) {
        Log.d("VMViewModel", "请求成功")

        if (res.isSuccess()) {
            dialogState.value = RxHttpState.Success(res.isMsg(), req.url, req.isFinish, req.silence)
            live.value = res
        } else {
            dialogState.value = RxHttpState.Error(res.isMsg(), req.url, req.isFinish, req.silence)
        }
    }

    /**
     * ----------------------------- 响应数据 通过接口进行回调 ----------------------------------------------------
     */
    fun <T> getData(bRequest: RxRequest, clas: Class<T>, sc: SuccessCall<BResponse<T>>) {
        val disposable = bRequest.getRxHttp().asResponse(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun <T> getDatas(
        bRequest: RxRequest,
        clas: Class<T>,
        sc: SuccessCall<BResponse<MutableList<T>>>
    ) {
        val disposable = bRequest.getRxHttp().asResponseList(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 根据状态判断走成功还是失败的回调，可以重写这个方法单独进行处理
     */
    fun <T> success(req: RxRequest, res: BResponse<T>, sc: SuccessCall<BResponse<T>>) {
        Log.d("VMViewModel", "请求成功")
        if (res.isSuccess()) {
            dialogState.value = RxHttpState.Success(res.isMsg(), req.url, req.isFinish, req.silence)
            sc.accept(res)
        } else {
            dialogState.value = RxHttpState.Error(res.isMsg(), req.url, req.isFinish, req.silence)
        }
    }

    /**
     * ----------------------------- 响应数据 String 通过LiveData 或 接口直接返回 ----------------------------------------------------
     */
    fun getDataString(bRequest: RxRequest, liveData: MutableLiveData<String>) {
        val disposable = bRequest.getRxHttp().asString()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({
                success(bRequest)
                liveData.value = it
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun getDataString(bRequest: RxRequest, sc: SuccessCall<String>) {
        val disposable = bRequest.getRxHttp().asString()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally(bRequest) }
            .subscribe({
                success(bRequest)
                sc.accept(it)
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    open fun success(req: RxRequest) {
        Log.d("VMViewModel", "请求成功")
        dialogState.value = RxHttpState.Success("操作完成", req.url, req.isFinish, req.silence)
    }

    private fun doOnSubscribe(silence: Boolean = false) {
        Log.d("VMViewModel", "请求开始")
        dialogState.value = RxHttpState.Loading(silence = silence)
    }

    private fun doFinally(bRequest: RxRequest) {
        Log.d("VMViewModel", "请求结束,不进行回调")
//        dialogState.value = RxHttpState.Completed("请求结束", bRequest.url)
    }

    /**
     * 组织错误信息
     */
    open fun error(req: RxRequest, throwable: Throwable?) {
        Log.d("VMViewModel", "请求失败")

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
        dialogState.value = RxHttpState.Error(msg, req.url, req.isFinish, req.silence)

        throwable?.printStackTrace()
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
    override fun onCleared() {
        super.onCleared()
        Log.d("VMViewModel", "onCleared")
        compositeDisposable?.dispose()
    }

}