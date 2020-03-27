package com.base.library.mvvm.template.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.base.BResponse
import com.base.library.base.url
import com.base.library.base.url3
import com.base.library.entitys.Banner
import com.base.library.entitys.Chapters
import com.base.library.base.BRequest
import com.base.library.mvvm.VMViewModel
import com.blankj.utilcode.util.GsonUtils

class Demo3ViewModel : VMViewModel() {

    private val liveChapters by lazy { MutableLiveData<BResponse<List<Chapters>>>() }
    private val liveBanner by lazy { MutableLiveData<BResponse<List<Banner>>>() }

    // 通过 OKGO 获取公众号列表,
    fun getChapters(): MutableLiveData<BResponse<List<Chapters>>> {
        val request = BRequest(url).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Chapters::class.java)
        }
        getData(request, liveChapters)
        return liveChapters
    }

    // 通过 Retrofit 获取首页 banner
    fun getBanner(): MutableLiveData<BResponse<List<Banner>>> {
        val request = BRequest(url3).apply {
            httpType = BRequest.GET
            gsonType = GsonUtils.getListType(Banner::class.java)
        }
        getRetrofit(request, liveBanner)
        return liveBanner
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