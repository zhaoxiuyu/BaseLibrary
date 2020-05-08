package com.base.library.mvvm.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.http.HttpConstant
import com.base.library.http.HttpManager
import com.blankj.utilcode.util.GsonUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 作用：基础的 ViewModel 类，封装了网络请求 返回 取消等处理
 */
open class VMViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val stateLiveData = MutableLiveData<BResponse<Any>>()

    /**
     * 使用 OKGO 进行网络请求
     */
    fun <T> getData(bRequest: BRequest, liveData: MutableLiveData<BResponse<T>>) {
        bRequest.tag = this
        bRequest.getOkGo().execute(object : AbsCallback<String>() {
            // 请求网络开始前，UI线程
            override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
                Log.v("OkGo", "onStart")
                if (!bRequest.silence) stateLiveData.value = BResponse.ResponseLoading()
                super.onStart(request)
            }

            // 对返回数据进行操作的回调，UI线程
            override fun onSuccess(response: Response<String>?) {
                Log.v("OkGo", "onSuccess")
                val body = response?.body() ?: ""
                success(bRequest, liveData, body)
            }

            // 缓存成功的回调,UI线程
            override fun onCacheSuccess(response: Response<String>?) {
                Log.v("OkGo", "onCacheSuccess")
                onSuccess(response)
                super.onCacheSuccess(response)
            }

            // 请求失败，响应错误，数据解析错误等，都会回调该方法，UI线程
            override fun onError(response: Response<String>?) {
                Log.v("OkGo", "onError")

                val throwable = response?.exception

                error(bRequest, liveData, throwable)
                super.onError(response)
            }

            // 请求网络结束后，UI线程
            override fun onFinish() {
                Log.v("OkGo", "onFinish")
                super.onFinish()
            }

            // 上传过程中的进度回调，get请求不回调，UI线程
            override fun uploadProgress(progress: Progress?) {
                Log.v("OkGo", "uploadProgress")
                liveData.postValue(BResponse.ResponseProgress(progress))
                super.uploadProgress(progress)
            }

            // 下载过程中的进度回调，UI线程
            override fun downloadProgress(progress: Progress?) {
                Log.v("OkGo", "downloadProgress")
                liveData.postValue(BResponse.ResponseProgress(progress))
                super.downloadProgress(progress)
            }

            // 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
            override fun convertResponse(response: okhttp3.Response?): String {
                Log.v("OkGo", "convertResponse")
                return response?.body()?.string() ?: ""
            }
        })
    }

    /**
     * 获取不同的Retrofit接口API
     */
    private fun getRetrofitApi(request: BRequest): Observable<String> {
        return when (request.url) {
            HttpConstant.url3 -> HttpManager.getServiceAPI().apiUrl(request.baseUrl)
            else -> HttpManager.getServiceAPI().apiUrl(request.baseUrl)
        }
    }

    fun <T> getRetrofit(bRequest: BRequest, liveData: MutableLiveData<BResponse<T>>) {
        getRetrofitApi(bRequest)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (!bRequest.silence) stateLiveData.value = BResponse.ResponseLoading()
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onNext(t: String) {
                    success(bRequest, liveData, t)
                }

                override fun onError(e: Throwable) {
                    error(bRequest, liveData, e)
                }

                override fun onComplete() {
                }
            })
    }

    /**
     * 验证数据是否合法
     */
    open fun <T> success(
        bRequest: BRequest,
        liveData: MutableLiveData<BResponse<T>>,
        body: String
    ) {
        val type = GsonUtils.getType(BResponse::class.java, bRequest.gsonType)
        val response = GsonUtils.fromJson<BResponse<T>>(body, type)

        // 成功就进行回调，否则走状态回调
        if (response.errorCode == 0) {
            response.isFinish = bRequest.isFinish
            response.state = BResponse.SUCCESS
            liveData.value = response
            stateLiveData.value = BResponse.ResponseSuccess(response.message, bRequest.isFinish)
        } else {
            stateLiveData.value = BResponse.ResponseError(null, response.message, bRequest.isFinish)
        }
    }

    /**
     * 组织错误信息
     */
    open fun <T> error(
        bRequest: BRequest,
        liveData: MutableLiveData<BResponse<T>>,
        throwable: Throwable?
    ) {
        val content = if (throwable is UnknownHostException || throwable is ConnectException) {
            "网络连接失败,请连接网络"
        } else if (throwable is SocketTimeoutException) {
            "网络请求超时"
        } else if (throwable is HttpException) {
            "响应码404和500,服务器内部错误"
        } else if (throwable is StorageException) {
            "SD卡不存在或者没有权限"
        } else {
            "出现异常"
        }

        /**
         * 不属于静默加载才弹窗
         */
        if (!bRequest.silence) {
            stateLiveData.value = BResponse.ResponseError(throwable, content, bRequest.isFinish)
        }

        throwable?.printStackTrace()
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

    /**
     * 页面销毁,切断所有订阅事件
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
        OkGo.getInstance().cancelTag(this)
    }

}