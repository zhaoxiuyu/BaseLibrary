package com.base.library.mvp.template.ui

import android.content.Intent
import com.base.library.R
import com.base.library.entitys.response.Article
import com.base.library.entitys.response.Chapters
import com.base.library.entitys.response.Login
import com.base.library.mvp.VPActivity
import com.base.library.mvp.template.contract.Demo1Contract
import com.base.library.mvp.template.presenter.Demo1Presenter

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : VPActivity<Demo1Contract.Presenter>(), Demo1Contract.View {

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        initContentView(R.layout.base_activity_test)
        mPresenter = Demo1Presenter(this)
    }

    override fun initData() {

    }

    override fun articleSuccess(article: Article) {

    }

    override fun chaptersSuccess(chapters: MutableList<Chapters>) {

    }

    override fun loginSuccess(login: Login) {

    }

}