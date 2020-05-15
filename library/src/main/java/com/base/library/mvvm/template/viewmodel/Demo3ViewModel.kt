package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.base.library.entitys.response.Banner
import com.base.library.entitys.response.Chapters
import com.base.library.http.HttpConstant
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.GsonUtils

class Demo3ViewModel : VMViewModel() {

    // 通过 OKGO 获取公众号列表,
    val liveChapters by lazy { MutableLiveData<BResponse<List<Chapters>>>() }



    fun getChapters() {
        val request = BRequest(HttpConstant.url).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Chapters::class.java)
        }
        getData(request, liveChapters)
    }

    // 通过 Retrofit 获取首页 banner
    val liveBanner by lazy { MutableLiveData<BResponse<List<Banner>>>() }

    fun getBanner() {
        val request = BRequest(HttpConstant.url3).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Banner::class.java)
        }
        getRetrofit(request, liveBanner)
    }

    /**
     * 一般来说成功标识是固定的,直接写在父类里面统一验证即可
     * 也可以通过重写父类的success自己定义成功规则的处理
     */
    override fun <T> success(
        request: BRequest,
        liveData: MutableLiveData<BResponse<T>>,
        body: String
    ) {
        super.success(request, liveData, body)
    }

}