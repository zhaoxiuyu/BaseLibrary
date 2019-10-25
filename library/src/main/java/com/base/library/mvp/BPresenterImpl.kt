package com.base.library.mvp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.base.library.http.BRequest
import com.blankj.utilcode.util.LogUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BPresenterImpl<T : BView?>(var mView: T?) : ViewModel(), BPresenter, BRequestCallback {

    private val model: BModel = BModelImpl()

    override fun getData(http: BRequest) {
        mView?.let {
            http.tag = this
            when (http.httpMode) {
                BRequest.getOkGo -> model.getData(this, http)
                BRequest.getOkRx2 -> model.getOkRx2(this, http)
                BRequest.getRetrofit2 -> model.getRetrofit2(this, http)
            }
        }
    }

    override fun beforeRequest() {
        mView?.showDialog()
    }

    override fun requestSuccess(body: String, bRequest: BRequest) {
        mView?.disDialog()
    }

    override fun requestError(throwable: Throwable?, bRequest: BRequest) {
        mView?.disDialog()

        val content = if (throwable is UnknownHostException || throwable is ConnectException) {
            "网络连接失败,请连接网络"
        } else if (throwable is SocketTimeoutException) {
            "网络请求超时"
        } else if (throwable is HttpException) {
            "响应码404和500,服务器内部错误"
        } else if (throwable is StorageException) {
            "SD卡不存在或者没有权限"
        } else {
            throwable?.message ?: "额...出错了"
        }
        LogUtils.e(content)

        /**
         * 不属于静默加载才弹窗
         */
        if (!bRequest.silence) {
            val fl = if (bRequest.isFinish) mView?.getDismissFinishListener() else null
            mView?.showDialog("异常提示", content, confirmListener = fl)
        }

        throwable?.printStackTrace()
    }

    override fun other(content: String, behavior: String, level: String) {
        mView?.other(content, behavior, level)
    }

    override fun onCleared() {
        recovery()
        LogUtils.d("onCleared 回收")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        recovery()
        LogUtils.d("onDestroy 回收")
    }

    private fun recovery() {
        OkGo.getInstance().cancelTag(this)
        model.closeAllDispose()
//        mView = null
    }

}