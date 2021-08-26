package com.base.module.function.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.BViewModel
import com.base.module.function.mvvm.repository.Demo3Repository
import com.blankj.utilcode.util.ThreadUtils
import io.reactivex.rxjava3.core.Observable

/**
 * 使用 ViewModelInject ，数据仓库要在构造方法里面初始化
 */
class Demo3ViewModel : BViewModel() {

    private val m3Repository by lazy { Demo3Repository() }

    /**
     * 获取首页文章列表
     */
    val articleLiveData by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        addDisposable(
            m3Repository.getArticle().compose(transformer(BConstant.article))
                .subscribe({
                    if (it.isSuccess()) {
                        articleLiveData.value = it
                    } else {
                        messageEvent(BConstant.article, it.showMsg())
                    }
                }, { messageEvent(BConstant.article, it.message ?: "") })
        )
    }

    /**
     * 获取公众号列表
     */
    val chaptersLiveData by lazy { MutableLiveData<BResponse<MutableList<WanChapters>>>() }
    fun getChapters() {
        addDisposable(
            m3Repository.getChapters().compose(transformer(BConstant.chapters))
                .subscribe({
                    ThreadUtils.isMainThread()
                    if (it.isSuccess()) {
                        chaptersLiveData.value = it
                    } else {
                        messageEvent(BConstant.chapters, it.showMsg())
                    }
                }, { messageEvent(BConstant.chapters, it.message ?: "") })
        )
    }

    /**
     * 登录
     */
    val loginLiveData by lazy { MutableLiveData<BResponse<WanLogin>>() }

    fun getLogin(map: Map<String, String>) {
        addDisposable(
            m3Repository.getLogin(map)
                .compose(transformerThread())
                .compose(transformerEvent())
                .subscribe({
                    if (it.isSuccess()) {
                        loginLiveData.value = it
                    } else {
                        messageEvent(msg = it.showMsg())
                    }
                }, { messageEvent(msg = it.message ?: "") })
        )
    }

    /**
     * 获取缓存
     */
    val getCacheLiveData by lazy { MutableLiveData<String>() }

    fun getCache(key: String) {
        Observable.create<String> {
            val cach = m3Repository.getCache(key)
            it.onNext(cach)
            it.onComplete()
        }.compose(transformerThread()).subscribe {
            getCacheLiveData.value = it ?: ""
        }
    }

    /**
     * 添加缓存
     */
    val putCacheLiveData by lazy { MutableLiveData<Boolean>() }

    fun putCache(key: String, content: String) {
        Observable.create<Boolean> {
            m3Repository.putCache(key, content)
            it.onNext(true)
            it.onComplete()
        }.compose(transformerThread()).subscribe {
            putCacheLiveData.value = it ?: false
        }
    }

}