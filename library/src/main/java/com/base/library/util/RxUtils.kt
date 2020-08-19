package com.base.library.util

import com.blankj.utilcode.util.CacheDiskStaticUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

//保存缓存
fun putCacheObservable(key: String, content: String, time: Int): Observable<String> {
    return Observable.just("保存缓存")
        .map {
            CacheDiskStaticUtils.put(key, content, time)
            "$key 缓存成功"
        }
        .compose(transformer())
}

//获取缓存
fun getCacheObservable(key: String): Observable<String> {
    return Observable.just("获取缓存")
        .map { CacheDiskStaticUtils.getString(key, "") }
        .compose(transformer())
}

//变换 IO线程 -> Main线程
fun <T> transformer(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}