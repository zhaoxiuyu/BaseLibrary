package com.base.library.mvvm.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.mvp.core.SuccessCall
import com.base.library.rxhttp.RxHttpState
import com.bumptech.glide.load.HttpException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class VMViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val dialogState = MutableLiveData<RxHttpState>()

    /**
     * ----------------------------- data 为单个实体对象 ----------------------------------------------------
     */
    fun <T> getData(bRequest: BRequest, liveData: MutableLiveData<BResponse<T>>, clas: Class<T>) {
        val disposable = bRequest.getRxHttp.asResponse(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, liveData, it) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun <T> getDatas(
        bRequest: BRequest,
        liveData: MutableLiveData<BResponse<MutableList<T>>>,
        clas: Class<T>
    ) {
        val disposable = bRequest.getRxHttp.asResponseList(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, liveData, it) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    open fun <T> success(req: BRequest, live: MutableLiveData<BResponse<T>>, res: BResponse<T>) {
        Log.d("VMViewModel", "请求成功")

        // 成功就进行回调，否则走状态回调
        if (res.errorCode == 0) {
            dialogState.value = RxHttpState.Success(res.errorMsg, req.isFinish)
            live.value = res
        } else {
            dialogState.value = RxHttpState.Error(null, res.errorMsg, req.isFinish)
        }
    }

    /**
     * ----------------------------- data 为 List ----------------------------------------------------
     */

    fun <T> getData(bRequest: BRequest, clas: Class<T>, sc: SuccessCall<BResponse<T>>) {
        val disposable = bRequest.getRxHttp.asResponse(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun <T> getDatas(
        bRequest: BRequest,
        clas: Class<T>,
        sc: SuccessCall<BResponse<MutableList<T>>>
    ) {
        val disposable = bRequest.getRxHttp.asResponseList(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 根据状态判断走成功还是失败的回调，可以重写这个方法单独进行处理
     */
    fun <T> success(req: BRequest, res: BResponse<T>, sc: SuccessCall<BResponse<T>>) {
        Log.d("VMViewModel", "请求成功")
        // 成功就进行回调，否则走状态回调
        if (res.errorCode == 0) {
            dialogState.value = RxHttpState.Success(res.errorMsg, req.isFinish)
            sc.accept(res)
        } else {
            dialogState.value = RxHttpState.Error(null, res.errorMsg, req.isFinish)
        }
    }

    /**
     * ---------------------------------------------------------------------------------
     */

    fun getDataString(bRequest: BRequest, liveData: MutableLiveData<String>) {
        val disposable = bRequest.getRxHttp.asString()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({
                success(bRequest)
                liveData.value = it
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    fun getDataString(bRequest: BRequest, sc: SuccessCall<String>) {
        val disposable = bRequest.getRxHttp.asString()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({
                success(bRequest)
                sc.accept(it)
            }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    open fun success(bRequest: BRequest) {
        Log.d("VMViewModel", "请求成功")
        dialogState.value = RxHttpState.Success("操作完成", bRequest.isFinish)
    }

    private fun doOnSubscribe(silence: Boolean = false) {
        Log.d("VMViewModel", "请求开始 是否静默加载 $silence")
        if (!silence) dialogState.value = RxHttpState.Loading()
    }

    private fun doFinally() {
        Log.d("VMViewModel", "请求结束")
    }

    /**
     * 组织错误信息
     */
    open fun error(bRequest: BRequest, throwable: Throwable?) {
        Log.d("VMViewModel", "请求失败")

        val msg = if (throwable is UnknownHostException || throwable is ConnectException) {
            "网络连接失败,请连接网络"
        } else if (throwable is SocketTimeoutException) {
            "网络请求超时"
        } else if (throwable is HttpException) {
            "响应码404和500,服务器内部错误"
        } else {
            throwable?.message ?: "出现异常"
        }

        // 不属于静默加载才弹窗
        if (!bRequest.silence) {
            dialogState.value = RxHttpState.Error(throwable, msg, bRequest.isFinish)
        }

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