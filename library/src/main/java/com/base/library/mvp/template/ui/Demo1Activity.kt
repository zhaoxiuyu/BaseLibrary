package com.base.library.mvp.template.ui

import android.content.Intent
import com.base.library.R
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.VPActivity
import com.base.library.mvp.template.contract.Demo1Contract
import com.base.library.mvp.template.presenter.Demo1Presenter
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.base_activity_test.*

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : VPActivity<Demo1Contract.Presenter>(), Demo1Contract.View {

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentViewBar(R.layout.base_activity_test)
        mPresenter = Demo1Presenter(this)
    }

    override fun lazyData() {
        getBTitleBar()?.setTvCenterText("MVP 测试网络请求")

        article.setOnClickListener { mPresenter?.getArticle() }
        chapters.setOnClickListener { mPresenter?.getChapters() }
        login.setOnClickListener {
            val map = mapOf(
                "username" to userName.text.toString(),
                "password" to passWord.text.toString()
            )
            mPresenter?.getLogin(map)
        }
    }

    override fun articleSuccess(article: WanArticle) {
        LogUtils.d("articleSuccess")
    }

    override fun chaptersSuccess(chapters: MutableList<WanChapters>) {
        LogUtils.d("chaptersSuccess")
    }

    override fun loginSuccess(login: WanLogin) {
        LogUtils.d("loginSuccess")
    }

}