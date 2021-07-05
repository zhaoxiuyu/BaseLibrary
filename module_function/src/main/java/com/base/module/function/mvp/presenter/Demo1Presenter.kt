package com.base.module.function.mvp.presenter

import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.core.SuccessCall
import com.base.library.mvp.core.VPPresenterImpl
import com.base.module.function.mvp.contract.Demo1Contract
import com.base.library.rxhttp.RxRequest
import rxhttp.wrapper.param.RxHttp

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : VPPresenterImpl<Demo1Contract.View>(view),
    Demo1Contract.Presenter {

    // 首页文章列表
    override fun getArticle() {
        val request = RxRequest(BConstant.article)
        request.httpGet().setDomainTowanandroidIfAbsent()

        getResponse(request, WanArticle::class.java, object : SuccessCall<BResponse<WanArticle>> {
            override fun accept(bResponse: BResponse<WanArticle>) {
                bResponse.data?.let { mView?.articleSuccess(it) }
                    ?: let { mView?.showDialog(content = "获取首页文章列表失败") }
            }
        })
    }

    // 获取公众号列表
    override fun getChapters() {
        val request = RxRequest(BConstant.chapters)
        request.httpGet().setDomainTowanandroidIfAbsent()

        getResponseList(
            request,
            WanChapters::class.java,
            object : SuccessCall<BResponse<MutableList<WanChapters>>> {
                override fun accept(bResponse: BResponse<MutableList<WanChapters>>) {
                    bResponse.data?.let { mView?.chaptersSuccess(it) }
                        ?: let { mView?.showDialog(content = "获取公众号列表失败") }
                }
            })
    }

    // 登录
    override fun getLogin(map: Map<String, String>) {
        val request = RxRequest(BConstant.login)
        request.httpPostForm().setDomainTowanandroidIfAbsent().addAll(map)

        request.getRxHttp().setDomainTowanandroidIfAbsent()
        getResponse(request, WanLogin::class.java, object : SuccessCall<BResponse<WanLogin>> {
            override fun accept(bResponse: BResponse<WanLogin>) {
                bResponse.data?.let { mView?.loginSuccess(it) }
                    ?: let { mView?.showDialog(content = "登录异常") }
            }
        })
    }

}
