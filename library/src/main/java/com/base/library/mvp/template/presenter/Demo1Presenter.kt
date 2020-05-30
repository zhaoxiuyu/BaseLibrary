package com.base.library.mvp.template.presenter

import com.base.library.base.BConstant
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.Article
import com.base.library.entitys.response.Chapters
import com.base.library.entitys.response.Login
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvp.core.VPPresenterImpl
import com.base.library.mvp.template.contract.Demo1Contract

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : VPPresenterImpl<Demo1Contract.View>(view),
    Demo1Contract.Presenter {

    // 首页文章列表
    override fun getArticle() {
        val request = BRequest(BConstant.article, BRequest.GET)
        request.getRxHttp.setDomainTowanandroidIfAbsent()
        getData(request, Article::class.java, object : SuccessCall<BResponse<Article>> {
            override fun accept(bResponse: BResponse<Article>) {
                bResponse.data?.let { mView?.articleSuccess(it) }
                    ?: let { mView?.showDialog(content = "获取首页文章列表失败") }
            }
        })
    }

    // 获取公众号列表
    override fun getChapters() {
        val request = BRequest(BConstant.chapters, BRequest.GET)
        request.getRxHttp.setDomainTowanandroidIfAbsent()
        getDatas(
            request,
            Chapters::class.java,
            object : SuccessCall<BResponse<MutableList<Chapters>>> {
                override fun accept(bResponse: BResponse<MutableList<Chapters>>) {
                    bResponse.data?.let { mView?.chaptersSuccess(it) }
                        ?: let { mView?.showDialog(content = "获取公众号列表失败") }
                }
            })
    }

    // 登录
    override fun getLogin(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
            getRxHttp.setDomainTowanandroidIfAbsent()
        }
        getData(request, Login::class.java, object : SuccessCall<BResponse<Login>> {
            override fun accept(bResponse: BResponse<Login>) {
                bResponse.data?.let { mView?.loginSuccess(it) }
                    ?: let { mView?.showDialog(content = "登录异常") }
            }
        })
    }

}
