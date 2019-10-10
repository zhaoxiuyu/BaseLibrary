package com.base.library.util

import com.base.library.database.DataBaseUtils
import com.base.library.database.entity.JournalRecord
import com.blankj.utilcode.util.CacheDiskStaticUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
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

//添加日志记录到数据库
fun roomInsertJournalRecord(content: String, behavior: String, level: String): Single<Long> {
    val journalRecord = JournalRecord()
    journalRecord.content = content
    journalRecord.behavior = behavior
    journalRecord.level = level

    return DataBaseUtils.getJournalRecordDao().insertRxCompletable(journalRecord)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

//变换 IO线程 -> Main线程
fun <T> transformer(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}