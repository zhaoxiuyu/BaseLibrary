package com.base.library.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.base.library.database.entity.JournalRecord
import io.reactivex.Single

@Dao
interface JournalRecordDao {

    @Insert
    fun insertRxCompletable(journalRecord: JournalRecord): Single<Long>

    @Query("delete from JournalRecord where time <= :timeJs")
    fun deleteFormTime(timeJs: String): Single<Int>

}
