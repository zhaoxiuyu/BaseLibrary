package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.WanArticle
import com.base.library.entitys.response.WanChapters
import com.base.library.entitys.response.WanLogin
import com.base.library.mvvm.core.BViewModel

class Demo3ViewModel : BViewModel() {

    private val mRepository by lazy { Demo3Repository(getRepository()) }

    /**
     * 获取首页文章列表
     */
    val articleLiveData by lazy { MutableLiveData<BResponse<WanArticle>>() }

    fun getArticle() {
        mRepository.getArticle(articleLiveData)
    }

    /**
     * 获取公众号列表
     */
    val chaptersLiveData by lazy { MutableLiveData<BResponse<MutableList<WanChapters>>>() }

    fun getChapters() {
        mRepository.getChapters(chaptersLiveData)
    }

    /**
     * 登录
     */
    val loginLiveData by lazy { MutableLiveData<BResponse<WanLogin>>() }

    fun getLogin(map: Map<String, String>) {
        mRepository.getLogin(map, loginLiveData)
    }

}