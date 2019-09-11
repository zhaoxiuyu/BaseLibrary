package com.base.library.util

import com.blankj.utilcode.util.CacheDiskStaticUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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