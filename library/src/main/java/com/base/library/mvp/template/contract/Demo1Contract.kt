package com.base.library.mvp.template.contract

import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.VPPresenter
import com.base.library.mvp.core.VPView

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