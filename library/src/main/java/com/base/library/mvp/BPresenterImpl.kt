package com.base.library.mvp

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest
import com.base.library.util.JsonUtils
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BPresenterImpl<T : BView>(var mView: T) : BPresenter, BRequestCallback {

    private val model: BModel = BModelImpl()
    private var lifecycleOwner: LifecycleOwner? = null

    override fun onCreate(owner: LifecycleOwner) {
        lifecycleOwner = owner
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        OkGo.getInstance().cancelTag(this)
        model.closeAllDispose()
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun getData(http: BRequest) {
        mView?.let {
            http.tag = this
            when (http.httpMode) {
                BRequest.getOkGo -> model.getData(this, http)
                BRequest.getOkRx2 -> model.getOkRx2(this, http)
                BRequest.getRetrofit2 -> model.getRetrofit2(this, http)
            }
        }
    }

    override fun beforeRequest() {
        mView?.showDialog()
    }

    override fun requestComplete() {}

    override fun requestSuccess(baseResponse: BaseResponse, baseHttpDto: BRequest) {
        mView?.disDialog()
    }

    @SuppressLint("CheckResult")
    override fun requestSuccess(body: String, baseHttpDto: BRequest) {
        Observable.just(body)
            .subscribeOn(Schedulers.io())
            .map {
                LogUtils.d("解析线程 : " + Thread.currentThread().name)
                JsonUtils.toAny(it, BaseResponse::class.java)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
            .subscribe({
                LogUtils.d("返回线程 : " + Thread.currentThread().name)
                requestSuccess(it, baseHttpDto)
            }, {
                requestError(it, baseHttpDto)
            })
    }

    override fun requestError(throwable: Throwable?, baseHttpDto: BRequest) {
        mView?.disDialog()

        var content = "额...出错了"
        if (throwable is UnknownHostException || throwable is ConnectException) {
            content = "网络连接失败,请连接网络"
        } else if (throwable is SocketTimeoutException) {
            content = "网络请求超时"
        } else if (throwable is HttpException) {
            content = "响应码404和500,服务器内部错误"
        } else if (throwable is StorageException) {
            content = "SD卡不存在或者没有权限"
        } else if (throwable is IllegalStateException) {
            content = throwable.message ?: "额...出错了"
        }
        LogUtils.e(content)

        /**
         * 不属于静默加载才弹窗
         */
        if (!baseHttpDto.silence) {
            val fl = if (baseHttpDto.isFinish) mView?.getConfirmFinishListener() else null
            mView?.showDialog("异常提示", content, confirmListener = fl)
        }

        throwable?.printStackTrace()
    }

}