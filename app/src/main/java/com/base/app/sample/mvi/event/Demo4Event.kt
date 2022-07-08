package com.base.app.sample.mvi.event

import com.base.app.entitys.response.WanAndroid
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.kunminx.architecture.domain.event.Event

class Demo4Event(eventId: Int) : Event<Demo4Event.Param, Demo4Event.Result>() {

    class Param {
        var map: Map<String, String> = mapOf()
    }

    class Result {
        var wanArticle: WanArticle? = null
        var wanLogin: WanLogin? = null
        var wanChapters: List<WanChapters>? = null
        var wanAndroid: List<WanAndroid>? = null

        var loadingMsg: String = "请稍等"
    }

    companion object {
        const val EVENT_SHOW_LOADING = 1000 // 加载框
        const val EVENT_DISMISS_LOADING = 1001 // 关闭加载框

        const val EVENT_SHOW_TOAST = 1002 // toast
        const val EVENT_SHOW_DIALOG = 1003 // 消息提示框

        const val EVENT_START_ACTIVITY = 1004 // 启动 Activity
        const val EVENT_FINISH_ACTIVITY = 1005 // 关闭 Activity

        const val EVENT_ARTICLE = 7 // 获取首页文章列表
        const val EVENT_CHAPTERS = 8 // 获取公众号列表
        const val EVENT_LOGIN = 9 // 登录
    }

    init {
        this.eventId = eventId
        this.param = Param()
        this.result = Result()
    }

    fun setLoginMap(map: Map<String, String>): Demo4Event {
        this.param.map = map
        return this
    }

}
