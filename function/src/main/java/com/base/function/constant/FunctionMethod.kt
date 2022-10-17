package com.base.function.constant

import com.base.common.constant.CommonARoute
import com.base.common.entitys.PageDescribe

object FunctionMethod {

    // Function 列表数据
    fun getFunctionDescribe(): MutableList<PageDescribe> {
        val datas = mutableListOf<PageDescribe>()

        datas.add(
            PageDescribe("Demo1", "MVP", null, CommonARoute.Demo1Activity)
        )
        datas.add(PageDescribe("Demo4", "MVI", null, CommonARoute.Demo4Activity))
        datas.add(PageDescribe("协程", "协程 相关操作", null))
        datas.add(PageDescribe("异步流", "异步流 相关操作", null))

        return datas
    }

}