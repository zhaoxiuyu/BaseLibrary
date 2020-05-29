package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BConstant
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.Article
import com.base.library.entitys.response.Chapters
import com.base.library.entitys.response.Login
import com.base.library.mvvm.core.VMViewModel

class Demo3ViewModel : VMViewModel() {

    // 首页文章列表
    val article by lazy { MutableLiveData<BResponse<Article>>() }

    fun getArticle() {
        val request = BRequest(BConstant.article, BRequest.GET)
        request.getRxHttp.setDomainTowanandroidIfAbsent()
        getData(request, article, Article::class.java)
    }

    // 获取公众号列表
    val chapters by lazy { MutableLiveData<BResponse<MutableList<Chapters>>>() }

    fun getChapters() {
        val request = BRequest(BConstant.chapters, BRequest.GET)
        request.getRxHttp.setDomainTowanandroidIfAbsent()
        getDatas(request, chapters, Chapters::class.java)
    }

    // 登录
    val login by lazy { MutableLiveData<BResponse<Login>>() }

    fun getLogin(map: Map<String, String>) {
        val request = BRequest(BConstant.login, BRequest.PostForm).apply {
            params = map
            getRxHttp.setDomainTowanandroidIfAbsent()
        }
        getData(request, login, Login::class.java)
    }

}