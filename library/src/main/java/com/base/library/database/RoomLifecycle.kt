package com.base.library.database

import androidx.lifecycle.LifecycleOwner
import com.base.library.database.entity.JournalRecord
import com.base.library.interfaces.MyLifecycle
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RoomLifecycle : MyLifecycle {

    private lateinit var owner: LifecycleOwner

    override fun onCreate(owner: LifecycleOwner) {
        this.owner = owner
    }

    // 添加日志
    fun addJournal(journalRecord: JournalRecord) {
        DataBaseUtils.getJournalRecordDao().insertRxCompletable(journalRecord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
            .subscribe(Consumer {
                LogUtils.d("插入的主键是:$it")
            }, Consumer {
                LogUtils.e("删除:$it.localizedMessage")
            })
    }

    // 删除多少天之前的日志,默认7天
    fun removeBefore(day: Long = 7) {
        // 7天前的日期
        val day7 = TimeUtils.getString(TimeUtils.getNowString(), -day, TimeConstants.DAY)
        DataBaseUtils.getJournalRecordDao().deleteFormTime(day7)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
            .subscribe(Consumer {
                LogUtils.d("删除了多少条:$it")
            }, Consumer {
                LogUtils.e("删除: $it.localizedMessage")
            })
    }

}