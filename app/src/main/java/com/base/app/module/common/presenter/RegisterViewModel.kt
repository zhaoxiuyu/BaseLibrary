package com.base.app.module.common.presenter

import androidx.lifecycle.MutableLiveData
import com.base.library.entitys.BaseResponse
import com.base.library.http.BRequest
import com.base.library.mvp.BPresenterImpl
import com.base.library.mvp.BView
import com.base.library.util.JsonUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(mView: BView) : BPresenterImpl<BView>(mView) {

    private val liveData = MutableLiveData<BaseResponse>()

    fun getBanner(): MutableLiveData<BaseResponse> {
        getData(BRequest("https://www.wanandroid.com/banner/json"))
        return liveData
    }

    override fun requestSuccess(body: String, baseHttpDto: BRequest) {
        super.requestSuccess(body, baseHttpDto)
        Observable.just(body)
            .map {
                JsonUtils.toAny(body, BaseResponse::class.java)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveData.value = it
            }
    }

}