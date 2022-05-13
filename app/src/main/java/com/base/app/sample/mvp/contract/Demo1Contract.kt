package com.base.app.sample.mvp.contract

import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.base.library.mvp.VPPresenter
import com.base.library.mvp.VPView

/**
 * 作用: 使用案例,自己定义Contract
 */
interface Demo1Contract {

    interface View : VPView {
        fun articleSuccess(article: WanArticle)

        fun chaptersSuccess(chapters: MutableList<WanChapters>)

        fun loginSuccess(login: WanLogin)
    }

    interface Presenter : VPPresenter {
        // 首页文章列表
        fun getArticle()

        // 获取公众号列表
        fun getChapters()

        // 登录
        fun getLogin(map: Map<String, String>)
    }

}