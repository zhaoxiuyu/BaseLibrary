package com.base.library.mvp.core

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.base.library.entitys.BRequest
import com.base.library.entitys.BResponse
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.load.HttpException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 作用：P层的基础实现类
 * 实现了网络请求 返回 取消等处理
 */
open class VPPresenterImpl<T : VPView?>(var mView: T?) : VPPresenter, VPCallback {

    private var compositeDisposable: CompositeDisposable? = null

    override fun <T> getData(bRequest: BRequest, clas: Class<T>, sc: SuccessCall<BResponse<T>>) {
        val disposable = bRequest.getRxHttp().asResponse(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    override fun <T> getDatas(
        bRequest: BRequest,
        clas: Class<T>,
        sc: SuccessCall<BResponse<MutableList<T>>>
    ) {
        val disposable = bRequest.getRxHttp().asResponseList(clas)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ success(bRequest, it, sc) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    override fun getDataString(bRequest: BRequest, sc: SuccessCall<String>) {
        val disposable = bRequest.getRxHttp().asString()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { doOnSubscribe(bRequest.silence) }
            .doFinally { doFinally() }
            .subscribe({ sc.accept(it) }, { error(bRequest, it) })
        addDisposable(disposable)
    }

    /**
     * 根据状态判断走成功还是失败的回调，可以重写这个方法单独进行处理
     */
    override fun <T> success(
        req: BRequest,
        res: BResponse<T>,
        sc: SuccessCall<BResponse<T>>
    ) {
        Log.d("VPPresenterImpl", "请求成功")
        mView?.disDialog()

        if (res.errorCode == 0) {
            sc.accept(res)
        } else {
            error(req, Throwable(res.errorMsg))
        }
    }

    override fun error(bRequest: BRequest, throwable: Throwable?) {
        mView?.disDialog()

        val content =
            if (throwable is UnknownHostException || throwable is ConnectException) {
                "网络连接失败,请连接网络"
            } else if (throwable is SocketTimeoutException) {
                "网络请求超时"
            } else if (throwable is HttpException) {
                "响应码404和500,服务器内部错误"
            } else {
                throwable?.message ?: "额...出错了"
            }
        LogUtils.e(content)

        /**
         * 不属于静默加载才弹窗
         */
        if (!bRequest.silence) {
            val fl = if (bRequest.isFinish) mView?.getDismissFinishListener() else null
            mView?.showDialog(title = "异常提示", content = content, confirmLi = fl)
        }

        throwable?.printStackTrace()
    }

    override fun doOnSubscribe(silence: Boolean) {
        Log.d("VPPresenterImpl", "请求开始 是否静默加载 $silence")
        if (!silence) {
            mView?.showDialog()
        }
    }

    override fun doFinally() {
        Log.d("VPPresenterImpl", "请求结束")
    }

    override fun other(content: String, behavior: String, level: String) {
        mView?.other(content, behavior, level)
    }

    /**
     * 添加一个订阅事件
     */
    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("VPPresenterImpl", "onDestroy")
        compositeDisposable?.dispose()
    }

}