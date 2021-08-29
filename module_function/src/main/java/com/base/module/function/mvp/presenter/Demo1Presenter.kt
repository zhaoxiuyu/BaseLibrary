package com.base.module.function.mvp.presenter

import com.base.library.base.BConstant
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvp.VPPresenterImpl
import com.base.library.util.RxHttpUtils
import com.base.module.function.mvp.contract.Demo1Contract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : VPPresenterImpl<Demo1Contract.View>(view),
    Demo1Contract.Presenter {

    // 首页文章列表
    override fun getArticle() {
        addDisposable(
            mHttpData.getArticle().asResponse(WanArticle::class.java).compose(transformer())
                .subscribe { bResponse ->
                    bResponse.data?.let {
                        mView?.articleSuccess(it)
                    } ?: let {
                        mView?.messageEvent(msg = "获取首页文章列表失败")
                    }
                }
        )

    }

    // 获取公众号列表
    override fun getChapters() {
        addDisposable(
            mHttpData.getChapters().asResponseList(WanChapters::class.java)
                .compose(transformerThread())
                .compose(transformerEvent(BConstant.chapters))
                .subscribe { bResponse ->
                    bResponse.data?.let {
                        mView?.chaptersSuccess(it)
                    } ?: let {
                        mView?.messageEvent(msg = "获取公众号列表失败")
                    }
                }
        )

    }

    // 登录
    override fun getLogin(map: Map<String, String>) {
        addDisposable(
            mHttpData.getLogin(map).asResponse(WanLogin::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mView?.loadingEvent() }
                .doFinally { mView?.dismissEvent() }
                .subscribe({ bResponse ->
                    bResponse.data?.let {
                        mView?.loginSuccess(it)
                    } ?: let {
                        mView?.messageEvent(msg = "登录异常")
                    }
                }, {
                    mView?.messageEvent(msg = RxHttpUtils.getThrowableMessage(it))
                })
        )

    }

}
