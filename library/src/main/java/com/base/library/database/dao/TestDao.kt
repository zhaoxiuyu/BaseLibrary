package com.base.library.database.dao

import androidx.room.*
import com.base.library.database.entity.Test
import io.reactivex.*


@Dao // 访问数据库操作的接口
interface TestDao {

    /**
     * @Insert 插入
     * @Update 更新
     * @Delete 删除
     * @Query 执行sql
     */

    /**
     * +++++++++++++++++++ 直接操作，需要手动开启子线程调用 +++++++++++++++++++
     */
    @Insert
    fun insertTest(test: Test)

    @Insert
    fun insertTestList(list: MutableList<Test>)

    @Update
    fun updateTest(test: Test)

    @Update
    fun updateTestList(list: MutableList<Test>)

    @Delete
    fun deleteTest(test: Test)

    @Delete
    fun deleteTestList(list: MutableList<Test>)

    @Query("delete from Test")
    fun deleteTestAll()

    @Query("select * from Test order by id desc") // 降序
    fun getTestAll(): MutableList<Test>

    @Query("select * from Test where word like:testWord") // 降序
    fun getTestAllByWord(testWord: String): MutableList<Test>

    /**
     * ++++++++++++++++++++++++++++ 结合 RxJava2 ++++++++++++++++++++++++++++
     */

    /**
     * +++++++++++ 这些都是查询 +++++++++++
     */

    // 如果没有查询到,那么Maybe就会被complete
    // 如果查询到数据,那么Maybe就会触发onSuccess并且被complete
    // 如果查询的数据在Maybe.onComplete调用之后被更新了,啥都不会发生
    @Query("select * from Test where id = :testId")
    fun getRxMaybe(testId: Int): Maybe<Test>

    // 如果没有查询到,那么Single就会触发onError
    // 如果查询到数据,那么Single就会触发onSuccess
    // 数据库中的信息在Single.onComplete调用之后被更新了,啥都不会发生,数据流已经完成
    @Query("select * from Test where word = :testWord")
    fun getRxSingle(testWord: String): Single<Test>

    // Flowable
    // 如果没有查询到,那么Flowable就不会发射事件,不运行onNext和onError,会发射empty,通过switchIfEmpty()处理逻辑
    // 如果查询到数据,那么Flowable就会触发onNext
    // 如果数据库信息被更新了,Flowable就会自动发射事件,允许你用更新的数据来更新UI界面
    @Query("select * from Test where msg = :testMsg")
    fun getRxFlowable(testMsg: String): Flowable<Test>

    @Query("select * from Test where msg = :testMsg")
    fun getRxObservable(testMsg: String): Observable<Test>

    /**
     * +++++++++++ 这些都是 Insert +++++++++++
     */

    // Completable 当insert完成时,马上调用 onComplete
    // Single<Long>/ Maybe<Long> — onSuccess发射的值，是插入项的rowID
    // Single<List<Long>> /Maybe<List<Long>> —  onSuccess发射的值，是插入列表的rowID列表
    @Insert
    fun insertRxCompletable(test: Test): Completable

    @Insert
    fun insertRxMaybe(test: Test): Maybe<Long>

    @Insert
    fun insertRxSingle(test: Test): Single<Long>

//    @Insert
//    fun insertRxMaybe2(test: MutableList<Test>): Maybe<MutableList<Long>>

//    @Insert
//    fun insertRxSingl2(test: MutableList<Test>): Single<List<Long>>

    /**
     * +++++++++++ 这些都是 Insert +++++++++++
     */

    //Completable — 当update/delete完成时，马上调用onComplete
    //Single<Long>/ Maybe<Long> — onSuccess发射的值，是update/delete影响的行数
    @Update
    fun update1(test: Test): Completable

    @Update
    fun update2(test: Test): Single<Int>

    @Update
    fun updateAll3(test: MutableList<Test>): Single<Int>

    @Delete
    fun deleteAll4(test: MutableList<Test>): Single<Int>

    @Delete
    fun deleteAll5(test: MutableList<Test>): Single<Int>

}