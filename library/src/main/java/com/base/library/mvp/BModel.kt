package com.base.library.mvp

import com.base.library.http.BRequest
import io.reactivex.disposables.Disposable

/**
 * 作用: 基本的Model类，简单的用来获取一个网络数据
 */
interface BModel {

    fun getData(callback: BRequestCallback, http: BRequest)

    fun getOkRx2(callback: BRequestCallback, http: BRequest)

    fun getRetrofit2(callback: BRequestCallback, http: BRequest)

    fun addDispose(disposable: Disposable)

    fun closeAllDispose()

}
