package com.base.library.mvp

import com.base.library.base.IDCARD
import com.base.library.http.BManager
import com.base.library.http.BRequest
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.model.Response
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BModelImpl : BModel {

    private var compositeDisposable: CompositeDisposable? = null

    override fun getData(callback: BRequestCallback, http: BRequest) {
        http.getOkGo().execute(object : BCallback(callback, http.silence) {
            override fun onSuccess(response: Response<String>?) {
                super.onSuccess(response)
                val body = response?.body() ?: ""

                callback.requestSuccess(body, http)
            }

            override fun onError(response: Response<String>?) {
                val throwable = response?.exception
                callback.requestError(throwable, http)
                super.onError(response)
            }
        })
    }

    override fun getOkRx2(callback: BRequestCallback, http: BRequest) {
        http.getOkRx2()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { if (!http.silence) callback.beforeRequest() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    addDispose(d)
                }

                override fun onComplete() {
                    callback.requestComplete()
                }

                override fun onNext(s: String) {
                    callback.requestSuccess(s, http)
                }

                override fun onError(e: Throwable) {
                    callback.requestError(e, http)
                }
            })
    }

    override fun getRetrofit2(callback: BRequestCallback, http: BRequest) {
        Observable.just(http)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { if (!http.silence) callback.beforeRequest() }
            .flatMap { getRetrofitApi(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    addDispose(d)
                }

                override fun onNext(body: String) {
                    printLog(http.url, http.method, body)
                    callback.requestSuccess(body, http)
                }

                override fun onError(e: Throwable) {
                    callback.requestError(e, http)
                }

                override fun onComplete() {
                    callback.requestComplete()
                }
            })
    }

    private fun getRetrofitApi(http: BRequest): Observable<String> {
        val httpService = BManager.mBaseHttpService
        return when (http.url) {
            IDCARD -> httpService.apiPay(http.body)
            else -> httpService.apiPay(http.body)
        }
    }

    override fun addDispose(disposable: Disposable) {
        compositeDisposable ?: let { compositeDisposable = CompositeDisposable() }
        compositeDisposable?.add(disposable)
    }

    override fun closeAllDispose() {
        compositeDisposable?.dispose()
    }

    private fun printLog(url: String, method: String, data: String) {
        LogUtils.i(
            StringBuilder()
                .appendln("Response 地址 : $url")
                .appendln("方法 : $method")
                .appendln("返回数据 : ")
                .appendln(data)
                .toString()
        )
    }

}