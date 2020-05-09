package com.base.app.module.common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.Banner
import com.base.library.entitys.response.Chapters
import com.base.library.http.HttpConstant
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.GsonUtils

class RegisterViewModel : VMViewModel() {

    // 公众号列表
    val liveChapters by lazy { MutableLiveData<BResponse<List<Chapters>>>() }

    fun getChapters() {
        val request = BRequest(HttpConstant.url).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Chapters::class.java)
        }
        getData(request, liveChapters)
    }

    // 首页banner
    val liveBanner by lazy { MutableLiveData<BResponse<List<Banner>>>() }

    fun getBanner() {
        val request = BRequest(HttpConstant.url3).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Banner::class.java)
        }
        getRetrofit(request, liveBanner)
    }

    // 如果没有配置header就走默认的url，配置了就走header里面的url
    fun getHh(): MutableLiveData<BResponse<List<Chapters>>> {
        val liveHh = MutableLiveData<BResponse<List<Chapters>>>()

        val request = BRequest(HttpConstant.url).apply {
            httpType = BRequest.GET
            baseUrl = HttpConstant.url3
            gsonType = GsonUtils.getListType(Chapters::class.java)
        }
        getRetrofit(request, liveHh)
        return liveHh
    }

}