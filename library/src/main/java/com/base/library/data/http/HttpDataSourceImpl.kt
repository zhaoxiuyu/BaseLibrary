package com.base.library.data.http

import android.graphics.Bitmap
import com.base.library.entitys.BResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import rxhttp.wrapper.param.RxHttp

/**
 * 网络数据源
 */
class HttpDataSourceImpl {

    companion object {
        val getInstance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = HttpDataSourceImpl()
    }

    /**
     * 响应数据 BResponse<Student.class>
     */
    fun <T> asResponse(rxHttp: RxHttp<*, *>, clas: Class<T>): Observable<BResponse<T>> {
        return rxHttp.asResponse(clas).compose(transformer())
    }

    /**
     * 响应数据 BResponse<MutableList<Student.class>>
     */
    fun <T> asResponseList(rxHttp: RxHttp<*, *>, clas: Class<T>):
            Observable<BResponse<MutableList<T>>> {
        return rxHttp.asResponseList(clas).compose(transformer())
    }

    /**
     * 响应数据 Student.class
     */
    fun <T> asDataClass(rxHttp: RxHttp<*, *>, clas: Class<T>): Observable<T> {
        return rxHttp.asClass(clas).compose(transformer())
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    fun <T> asDataList(rxHttp: RxHttp<*, *>, clas: Class<T>): Observable<MutableList<T>> {
        return rxHttp.asList(clas).compose(transformer())
    }

    /**
     * 响应数据 String
     */
    fun asString(rxHttp: RxHttp<*, *>): Observable<String> {
        return rxHttp.asString().compose(transformer())
    }

    /**
     * 响应数据 Bitmap
     */
    fun asBitmap(rxHttp: RxHttp<*, *>): Observable<Bitmap> {
        return rxHttp.asBitmap<Bitmap>().compose(transformer())
    }

    /**
     * 响应数据 MutableList<Student.class>
     */
    fun <T> asMap(rxHttp: RxHttp<*, *>, clas: Class<T>): Observable<Map<T, T>> {
        return rxHttp.asMap(clas).compose(transformer())
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    fun <T> transformer(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}