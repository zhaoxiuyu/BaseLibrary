package com.base.library.util

import com.blankj.utilcode.util.CacheDiskStaticUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * RxJava 保存 获取 缓存
 */
object RxCacheUtils {

    /**
     * 将字符串值放入缓存.
     *
     * @param key      缓存的密钥.
     * @param value    缓存的值.
     * @param saveTime 缓存的保存时间，以秒为单位.
     */
    fun putCache(key: String, content: String, time: Int): Observable<String> {
        return Observable.just("保存缓存")
            .map {
                CacheDiskStaticUtils.put(key, content, time)
                "$key 缓存成功"
            }
            .compose(transformer())
    }

    /**
     * 返回缓存中的字符串值.
     *
     * @param key          缓存的密钥.
     * @param defaultValue 缓存不存在时的默认值.
     * @return 如果存在缓存，则为字符串值，否则为defaultValue
     */
    fun getCacheString(key: String, defaultValue: String = ""): Observable<String> {
        return Observable.just("获取缓存")
            .map { CacheDiskStaticUtils.getString(key, "") }
            .compose(transformer())
    }

    /**
     * 变换 IO线程 -> Main线程
     */
    fun <T> transformer(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}