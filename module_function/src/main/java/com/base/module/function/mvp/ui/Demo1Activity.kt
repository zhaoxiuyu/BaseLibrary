package com.base.module.function.mvp.ui

import android.content.Intent
import android.os.Bundle
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.VPActivity
import com.base.module.function.databinding.BaseActivityTestBinding
import com.base.module.function.mvp.contract.Demo1Contract
import com.base.module.function.mvp.presenter.Demo1Presenter
import com.blankj.utilcode.util.LogUtils

/**
 * 作用: 使用案例,Activity使用自己定义的Contract和Presenter
 */
class Demo1Activity : VPActivity(), Demo1Contract.View {
//class Demo1Activity : VPActivity<Demo1Contract.Presenter>(), Demo1Contract.View {

    private val mPresenter by lazy { Demo1Presenter(this) }

    private val viewBinding by lazy { BaseActivityTestBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentViewBar(viewBinding.root, topPadding = viewBinding.root)
        lifecycle.addObserver(mPresenter)

//        viewBinding.titleBar.title = "MVP 测试网络请求"
//        viewBinding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
//            override fun onLeftClick(view: View?) {
//                setResult(Activity.RESULT_OK, Intent().putExtra("msg", "传个msg吧"))
//                finish()
//            }
//
//            override fun onTitleClick(view: View?) {
//            }
//
//            override fun onRightClick(view: View?) {
//            }
//        })
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