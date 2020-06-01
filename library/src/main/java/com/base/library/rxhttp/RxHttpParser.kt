package com.base.library.rxhttp

import com.base.library.entitys.BPageList
import com.base.library.entitys.BResponse
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type

@Parser(name = "Response", wrappers = [MutableList::class, BPageList::class])
open class RxHttpParser<T> : AbstractParser<BResponse<T>> {

    /**
     * 此构造方法适用于任意Class对象，但更多用于带泛型的Class对象，如：List<Student>
     * 用法:
     * Java: .asParser(new RxHttpParser<List<Student>>(){})
     * Kotlin: .asParser(object : RxHttpParser<List<Student>>() {})
     * 注：此构造方法一定要用protected关键字修饰，否则调用此构造方法将拿不到泛型类型
     */
    protected constructor() : super()

    /**
     * 此构造方法仅适用于不带泛型的Class对象，如: Student.class
     * 用法:
     * Java: .asParser(new RxHttpParser<>(Student.class))   或者 .asResponse(Student.class)
     * Kotlin: .asParser(RxHttpParser(Student::class.java)) 或者 .asResponse(Student::class.java)
     */
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): BResponse<T> {
        //  获取泛型类型
        val type: Type = ParameterizedTypeImpl[BResponse::class.java, mType]
        val data: BResponse<T> = convert(response, type)

        return data

        //  获取泛型类型
//        val type: Type = ParameterizedTypeImpl[BResponse::class.java, mType]
//        val data: BResponse<T> = convert(response, type)

        // 获取 data 字段
//        var t = data.data
//        /**
//         * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
//         * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
//         * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
//         */
//        if (t == null && mType == String::class.java) {
//            t = data.message as T
//        }
//        // 不等于0 说明数据不正确,抛出异常
//        if (data.code != 0 || t == null) {
//            throw ParseException(data.code.toString(), data.message, response)
//        }
//        return t
    }

}