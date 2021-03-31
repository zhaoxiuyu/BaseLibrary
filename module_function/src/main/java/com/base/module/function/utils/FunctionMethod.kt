package com.base.module.function.utils

import com.base.module.function.entity.FunctionDescribe
import com.base.module.function.mvp.ui.Demo1Activity

object FunctionMethod {

    fun getFunctionDescribe(): MutableList<FunctionDescribe> {
        val datas = mutableListOf<FunctionDescribe>()

        datas.add(FunctionDescribe("Demo3", "测试 MVVM 网络请求", null))
        datas.add(FunctionDescribe("Demo4", "测试 MVVM 协程请求", null))
        datas.add(FunctionDescribe("Demo1", "测试 MVP 网络请求", Demo1Activity::class.java))
        datas.add(FunctionDescribe("协程", "练手 协程 相关操作", null))
        datas.add(FunctionDescribe("异步流", "练手 异步流 相关操作", null))
        datas.add(FunctionDescribe("测试", "测试用的", null))

        return datas
    }

}