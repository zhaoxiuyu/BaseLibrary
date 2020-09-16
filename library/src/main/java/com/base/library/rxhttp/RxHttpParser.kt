package com.base.library.rxhttp

import com.base.library.entitys.BPageList
import com.base.library.entitys.BResponse
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.StringUtils
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.parse.AbstractParser
import java.lang.reflect.Type

/**
 * 自定义解析Response<T>类型数据
 */
@Parser(name = "Response", wrappers = [List::class, BPageList::class])
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
        // 解析code msg字段，把data当成String对象，防止泛型和返回的data不匹配导致解析失败
        val type: Type = ParameterizedTypeImpl[BResponse::class.java, String::class.java]
        val oidResponse: BResponse<String> = convert(response, type)

        // 把解析的赋值给新的 BResponse
        val newResponse = BResponse<T>()
        newResponse.errorMsg = oidResponse.errorMsg
        newResponse.errorCode = oidResponse.errorCode
        newResponse.status = oidResponse.status
        newResponse.message = oidResponse.message
        newResponse.msg = oidResponse.msg
        newResponse.code = oidResponse.code

        /**
         * data 不为空 并且 泛型不是 String，就转换成对应的泛型数据
         */
        if (!StringUtils.isEmpty(oidResponse.data)
            && oidResponse.data?.length ?: 0 > 5
            && mType != String::class.java
        ) {
            newResponse.data = GsonUtils.fromJson<T>(oidResponse.data, mType)
        }

        // 泛型是String，就把data强转成泛型,然后赋值给新的 BResponse
        if (mType == String::class.java) {
            newResponse.data = oidResponse.data as T
        }
        return newResponse

        //  获取泛型类型
//        val type: Type = ParameterizedTypeImpl[BResponse::class.java, mType]
//        val data: BResponse<T> = convert(response, type)
//
//        return data

        //  获取泛型类型
//        val type: Type = ParameterizedTypeImpl[BResponse::class.java, mType]
//        val data: BResponse<T> = convert(response, type)

        // 获取 data 字段
//        var t = data.data
//        /**
//         * 考虑到有些时候服务端会返回：{"code":0,"msg":"成功"}  类似没有data的数据
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