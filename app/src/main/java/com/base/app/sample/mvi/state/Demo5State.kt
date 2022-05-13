package com.base.app.sample.mvi.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.base.app.entitys.response.WanAndroid
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.base.library.base.BResponse
import com.base.library.mvvm.DataStatus

// 负责承载页面所有的数据
data class Demo5State(
    @PersistState val status: DataStatus = DataStatus.LOADED, // 数据状态
    @PersistState val str: String = "", // 测试用的
    val strAsync: Async<String> = Uninitialized, // 测试用的
    @PersistState val count: Int = 0, // 数量
    val login: BResponse<WanLogin> = BResponse(), //　登录
    val loginAsync: Async<BResponse<WanLogin>> = Uninitialized, //　异步监听 登录 接口
    val banners: BResponse<MutableList<WanAndroid>> = BResponse(), // 首页banner
    val chapters: MutableList<WanChapters> = mutableListOf(), // 获取公众号列表
    val article: BResponse<WanArticle> = BResponse(), // 首页文章列表
) : MavericksState