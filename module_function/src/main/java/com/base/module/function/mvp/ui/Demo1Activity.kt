package com.base.module.function.mvp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.mvp.VPActivity
import com.base.module.function.databinding.ActivityDemo1Binding
import com.base.module.function.mvp.contract.Demo1Contract
import com.base.module.function.mvp.presenter.Demo1Presenter
import com.blankj.utilcode.util.LogUtils

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : VPActivity<Demo1Contract.Presenter>(), Demo1Contract.View {

    private val viewBinding by lazy { ActivityDemo1Binding.inflate(layoutInflater) }

    override fun createPresenter(): Demo1Contract.Presenter {
        return Demo1Presenter(this)
    }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentViewBar(viewBinding.root, topPadding = viewBinding.root)

        viewBinding.titleBar.title = "MVP 测试网络请求"
        viewBinding.titleBar.setOnTitleBarListener(object : MyTitleBarListener() {
            override fun onLeftClick(v: View?) {
                setResult(Activity.RESULT_OK, Intent().putExtra("msg", "传个msg吧"))
                finish()
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.article.setOnClickListener {
            mPresenter?.getArticle()
        }
        viewBinding.chapters.setOnClickListener {
            mPresenter?.getChapters()
        }
        viewBinding.login.setOnClickListener {
            val map = mapOf(
                "username" to viewBinding.userName.text.toString(),
                "password" to viewBinding.passWord.text.toString()
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