package com.base.app.sample.mvp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.base.app.databinding.ActivityDemo1Binding
import com.base.app.entitys.response.WanArticle
import com.base.app.entitys.response.WanChapters
import com.base.app.entitys.response.WanLogin
import com.base.app.sample.mvp.contract.Demo1Contract
import com.base.app.sample.mvp.presenter.Demo1Presenter
import com.base.library.mvp.VPActivity
import com.base.library.mvp.VPPresenter
import com.blankj.utilcode.util.LogUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : VPActivity<ActivityDemo1Binding>(), Demo1Contract.View {

    private val mPresenter by lazy { Demo1Presenter(this) }

    override fun initArgs(mIntent: Intent?) {}

    override fun addObserverPresenter(): MutableList<VPPresenter> {
        return mutableListOf(mPresenter)
    }

    override fun initView() {
        setContentViewBar(viewBinding.root, topPadding = viewBinding.root)
        viewBinding.titleBar.title = "MVP 测试网络请求"
        viewBinding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                setResult(Activity.RESULT_OK, Intent().putExtra("msg", "传个msg吧"))
                finish()
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.article.setOnClickListener {
            mPresenter.getArticle()
        }
        viewBinding.chapters.setOnClickListener {
            mPresenter.getChapters()
        }
        viewBinding.login.setOnClickListener {
            val map = mapOf(
                "username" to viewBinding.userName.text.toString(),
                "password" to viewBinding.passWord.text.toString()
            )
            mPresenter.getLogin(map)
        }
    }

    override fun registerObserve() {
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