package com.base.library.dao

import org.litepal.LitePal
import org.litepal.crud.LitePalSupport

object LitePalUtils {

    /**
     * 插入
     */
    fun <T : LitePalSupport> insert(data: T) {
        data.save()
    }

    /**
     * 插入一个集合
     */
    fun <T : LitePalSupport> insert(datas: Collection<T>) {
        LitePal.saveAll(datas)
    }

    /**
     * 删除单个数据
     */
    fun delete(data: LitePalSupport) {
        data.delete()
    }

    /**
     * 根据 id 删除
     */
    fun delete(data: Class<LitePalSupport>, id: Long) {
        LitePal.delete(data, id)
    }

    /**
     * 参数1 ：表
     * 参数2 ：约束条件
     * 说明 ：第一个字符参数表示SQL的WHERE部分,第一个问号是第二个参数,第二个问号是第三个参数,以此类推。
     * 示例 ： LitePal.deleteAll(Journal.class, "behavior = ? and version = ?", "心跳", "1.0.0")
     */
    fun deleteAll(data: Class<LitePalSupport>, vararg conditions: String?) {
        LitePal.deleteAll(data, *conditions)
        // 参数 * 会复制新的数组Arrays.copyOf(value, value.length),不加 * 就转换成Any类型
    }

    /**
     * 传递空字符串将删除所有行
     */
    fun deleteAll(data: Class<LitePalSupport>) {
        LitePal.deleteAll(data, null)
    }

    /**
     * 根据 id 查询单个记录
     */
    fun find(data: Class<LitePalSupport>, id: Long) {
        LitePal.find(data, id)
    }

    /**
     * 查询所有的记录
     */
    fun findAll(data: Class<LitePalSupport>) {
        LitePal.findAll(data)
    }

    /**
     * 返回表的第一条数据
     */
    fun findFirst(data: Class<LitePalSupport>) {
        LitePal.findFirst(data)
    }

    /**
     * 返回表的最后一条数据
     */
    fun findLast(data: Class<LitePalSupport>) {
        LitePal.findLast(data)
    }

    /**
     * 根据多个 id 进行查询 多条数据
     *  findAll(Journal::class.java, 1, 2, 3)
     */
    fun findAll(data: Class<LitePalSupport>, vararg ids: Long) {
        LitePal.findAll(data, *ids)
    }

    /**
     * 参数1 ：表
     * 参数2 ：约束条件
     * 说明 ：第一个字符参数表示SQL的WHERE部分,第一个问号是第二个参数,第二个问号是第三个参数,以此类推。
     * 示例 ： LitePal.where("name like ? and age < ?", "%赵%", "20").find(data) 查询 name 包含 赵 并且 age < 20 的数据
     */
    fun findWhere(data: Class<LitePalSupport>, vararg conditions: String?) {
        LitePal.where(*conditions).find(data)
    }

    /**
     * 连缀查询
     * select 接受任意个字符串参数,每个参数对应一个列名
     * where 指定查询条件,参数1 用于进行条件约束,参数2 开始 都是用于替换第一个参数中的占位符
     * order 对应 sql语句 order by 部分
     * limit 限制查询返回的行数
     * offset 偏移量,和limit一起使用,原来是查询前10的数据,偏移10个位置后,变成查询第11到20的数据了,
     * 如果偏移20,就表示查询21到30的数据,以此类推,可以用来分页查询。
     */
    fun findWhere(data: Class<LitePalSupport>) {
        LitePal
            .select("name", "age") // 只查询 name 和 age 这两列数据
            .where("name like ? and age > ?", "%赵%", "0") // 查询name包含"赵" 并且 age>0 的数据
            .order("age desc") // 根据age进行排序,asc正序,desc倒序
            .limit(10) // 只查询前10条数据
            .offset(10) // 偏移10个位置,和 limit 一起使用
            .find(data) // 查询哪张表
    }

    /**
     * 激进查询
     * 一对多关联的时候
     * isEager true 关联查询,比如查询id=1的新闻可以把对应的评论一起查询出来
     */
    fun findWhere(data: Class<LitePalSupport>, id: Long, isEager: Boolean = false) {
        LitePal.find(data, id, isEager)
    }

    /**
     * 原生查询
     * findWhere("select * from Journal where age>?", "0")
     * 前面的是约束，后面的参数用于替换语句中的占位符
     * 返回的数Cursor对象,和原生的sql语句返回的结果相同
     */
    fun findWhere(sql: String, vararg conditions: String) {
        val cursor = LitePal.findBySQL(sql, *conditions)
    }

    /**
     * 聚合函数
     * count 统计行数
     * sum 对具有运算能力的列对结果进行求合,比如整型或者浮点型列
     * average 对具有运算能力的列统计平均数
     * max 求出某列最大的数值
     * min 求出某列最小的数值
     */
    fun findJuhe(data: Class<LitePalSupport>) {
        // 统计多少条数据
        LitePal.count(data)

        // 查询age=20的有多少条
        LitePal.where("age = ?", "20").count(data)

        // 参数2 需要求合的列,参数3 指定类型,根据情况指定类型
        LitePal.sum(data, "age", Int::class.java)

        // 统计平均年龄
        LitePal.average(data, "age")

        // 得到最大的年龄,参数2 列名,参数3 指定类型,根据情况指定类型
        LitePal.max(data, "age", Int::class.java)

        // 得到最小的年龄,参数2 列名,参数3 指定类型,根据情况指定类型
        LitePal.min(data, "age", Int::class.java)
    }

}