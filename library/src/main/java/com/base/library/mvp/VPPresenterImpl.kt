package com.base.library.mvp

import androidx.lifecycle.LifecycleOwner
import com.base.library.base.IDCARD
import com.base.library.entitys.BaseResponse
import com.base.library.base.BManager
import com.base.library.base.BRequest
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import com.lzy.okgo.model.Response
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 作用：P层的基础实现类
 * 实现了网络请求 返回 取消等处理
 */
open class VPPresenterImpl<T : VPView?>(var mView: T?) : VPPresenter, VPCallback {

    var owner: LifecycleOwner? = null

    override fun onCreate(owner: LifecycleOwner) {
        this.owner = owner
    }

    override fun onDestroy(owner: LifecycleOwner) {
        OkGo.getInstance().cancelTag(this)
    }

    override fun other(content: String, behavior: String, level: String) {
        mView?.other(content, behavior, level)
    }

    override fun beforeRequest() {
        mView?.showDialog()
    }

    override fun requestSuccess(body: String, bRequest: BRequest) {
        Observable.just(body).map { GsonUtils.fromJson(it, BaseResponse::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
            .subscribe({
                requestSuccess(it, bRequest)
            }, {
                requestError(it, bRequest)
            })
    }

    override fun requestSuccess(response: BaseResponse, bRequest: BRequest) {
        mView?.disDialog()
    }

    override fun requestError(throwable: Throwable?, bRequest: BRequest) {
        mView?.disDialog()

        val content = if (throwable is UnknownHostException || throwable is ConnectException) {
            "网络连接失败,请连接网络"
        } else if (throwable is SocketTimeoutException) {
            "网络请求超时"
        } else if (throwable is HttpException) {
            "响应码404和500,服务器内部错误"
        } else if (throwable is StorageException) {
            "SD卡不存在或者没有权限"
        } else {
            throwable?.message ?: "额...出错了"
        }
        LogUtils.e(content)

        /**
         * 不属于静默加载才弹窗
         */
        if (!bRequest.silence) {
            val fl = if (bRequest.isFinish) mView?.getDismissFinishListener() else null
            mView?.showDialog(title = "异常提示", content = content, confirmLi = fl)
        }

        throwable?.printStackTrace()
    }

    /**
     * OKGO
     */
    override fun getData(http: BRequest) {
        val requestBody = http.print()
        other(requestBody, "请求参数 ${http.method}", "I")
        http.getOkGo().execute(object : VPRequestCallback(this, http.silence) {
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                val body = response?.body() ?: ""

                other(body, "请求成功 ${http.method}", "I")
                requestSuccess(body, http)
            }

            override fun onError(response: Response<String>?) {
                val throwable = response?.exception
                other("${throwable?.localizedMessage}", "请求失败 ${http.method}", "E")
                requestError(throwable, http)
                super.onError(response)
            }
        })
    }

    /**
     * Retrofit2
     */
    override fun getRetrofit2(http: BRequest) {
        fun getRetrofitApi(): Observable<String> {
            return when (http.url) {
                IDCARD -> BManager.mBaseHttpService.apiPay(http.body)
                else -> BManager.mBaseHttpService.apiPay(http.body)
            }
        }
        Observable.just(http).subscribeOn(Schedulers.io())
            .doOnSubscribe { if (!http.silence) beforeRequest() }
            .flatMap { getRetrofitApi() }.observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
            .subscribe({
                requestSuccess(it, http)
            }, {
                requestError(it, http)
            })
    }

}