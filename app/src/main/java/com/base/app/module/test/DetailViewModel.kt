package com.base.app.module.test

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.base.library.base.BConstant
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.mvvm.core.BViewModel
import com.base.library.rxhttp.ResponseState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class DetailViewModel @ViewModelInject constructor(private val mRepository: DetailRepository) :
    BViewModel() {

//    private val mBResponse by lazy { BRepository() }
//    override fun getRepository() = mBResponse

    /**
     * 获取首页文章列表
     */
    fun getArticle() = liveData<BResponse<WanArticle>> {
        mRepository.getArticle()
            .onStart {
                sendState(ResponseState.Loading(BConstant.article))
            }
            .onCompletion {
                sendState(ResponseState.Completed(BConstant.article))
            }
            .collectLatest {
                emit(it)
            }
    }

}