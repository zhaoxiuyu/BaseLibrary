package com.base.app.module.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.mvvm.BViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class DetailViewModel : BViewModel() {

    private val mRepository by lazy { DetailRepository() }

//    private val mBResponse by lazy { BRepository() }
//    override fun getRepository() = mBResponse

    /**
     * 获取首页文章列表,协程写法
     */
    fun getArticle() = liveData<BResponse<WanArticle>> {
        mRepository.getArticle()
            .onStart { loadingEvent(BConstant.article) }
            .onCompletion { dismissEvent(BConstant.article) }
            .collectLatest { emit(it) }
    }

    //

    /**
     * Flow流写法
     */
    fun getArticle2(): LiveData<BResponse<WanArticle>> {
        return mRepository.getArticle2()
            .onStart {
                loadingEvent(BConstant.article)
            }
            .catch {
                messageEvent(BConstant.article)
            }
            .onCompletion {
                dismissEvent(BConstant.article)
            }
            .asLiveData()
    }

}
