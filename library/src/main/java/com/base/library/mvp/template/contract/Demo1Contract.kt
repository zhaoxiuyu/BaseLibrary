package com.base.library.mvp.template.contract

import com.base.library.entitys.response.Article
import com.base.library.entitys.response.Chapters
import com.base.library.entitys.response.Login
import com.base.library.mvp.core.VPPresenter
import com.base.library.mvp.core.VPView

/**
 * 作用: 使用案例,自己定义Contract
 */
interface Demo1Contract {

    interface View : VPView {
        fun articleSuccess(article: Article)

        fun chaptersSuccess(chapters: MutableList<Chapters>)

        fun loginSuccess(login: Login)
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