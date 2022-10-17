package com.base.common.sample.mvp.presenter

import com.base.common.http.OkHttpContracts
import com.base.common.sample.mvi.repository.Demo5Repository
import com.base.common.sample.mvp.contract.Demo1Contract
import com.base.library.mvp.VPPresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 作用: 使用案例,自己定义Presenter
 */
class Demo1Presenter(view: Demo1Contract.View) : VPPresenterImpl<Demo1Contract.View>(view),
    Demo1Contract.Presenter {

    private val mRepository by lazy { Demo5Repository() }

    // 首页文章列表
    override fun getArticle() {
        addDisposable(
            mRepository.getArticleObser().compose(transformer())
                .subscribe({ bResponse ->
                    bResponse.data?.let {
                        mView?.articleSuccess(it)
                    } ?: let {
                        mView?.messageEvent(msg = "获取首页文章列表失败")
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    // 获取公众号列表
    override fun getChapters() {
        addDisposable(
            mRepository.getChaptersObser().compose(transformerThread())
                .compose(transformerEvent(OkHttpContracts.chapters))
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
            mRepository.getLoginObser(map["username"] ?: "", map["password"] ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mView?.loadingEvent()
                }
                .doFinally {
                    mView?.dismissEvent()
                }
                .subscribe({ bResponse ->
                    bResponse.data?.let {
                        mView?.loginSuccess(it)
                    } ?: let {
                        mView?.messageEvent(msg = "登录异常")
                    }
                }, {
                    mView?.messageEvent(msg = it.message ?: "")
                })
        )
    }

}
