package com.base.library.mvvm.core

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BResponse
import com.base.library.mvp.core.SuccessCall
import com.base.library.rxhttp.ResponseState
import com.base.library.rxhttp.RxRequest
import com.base.library.util.OtherUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

open class BRepository @Inject constructor() {

    private var compositeDisposable: CompositeDisposable? = null

    var dialogState: MutableLiveData<ResponseState>? = null

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
        Log.d("BRepository", "doOnSubscribe 请求开始")
        dialogState?.value = ResponseState.Loading("bRequest", "")
    }

    private fun doFinally() {
        Log.d("BRepository", "doFinally 请求结束")
        dialogState?.value = ResponseState.Completed("bRequest")
    }

    open fun <T> success(
        request: RxRequest,
        response: T,
        live: MutableLiveData<T>? = null,
        call: SuccessCall<T>? = null
    ) {
        Log.d("BRepository", "请求成功")

        request.msg = "成功"

        // 请求成功是否弹窗
        if (request.showSuccess) {
            dialogState?.value = ResponseState.Success(request)
        }
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

        request.msg = OtherUtils.getThrowableMessage(throwable)

        // 请求失败是否弹窗
        if (request.showFail) {
            dialogState?.value = ResponseState.Error(request)
        }
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