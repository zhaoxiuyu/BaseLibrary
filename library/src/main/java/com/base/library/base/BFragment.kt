package com.base.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.interfaces.MyXPopupListener
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.ImmersionFragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * ImmersionOwner 用来在Fragment中实现沉浸式
 */
abstract class BFragment : ImmersionFragment() {

    abstract fun initArgs(bundle: Bundle?)
    abstract fun initView(bundle: Bundle?)
    abstract fun initData()

    private var mView: View? = null
    private var container: ViewGroup? = null
    private var inflater: LayoutInflater? = null

    val mApplication: BApplication by lazy { activity?.application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        this.container = container

        initArgs(arguments)
        initView(savedInstanceState)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).titleBar(mView).init()
    }

    open fun setContentView(layout: Int) {
        mView = inflater?.inflate(layout, container, false)
    }

    /**
     * ------------- 文件缓存 -------------
     */
    open fun getCacheDisk(key: String, consumer: Consumer<String>) {
        Observable.just("获取缓存")
            .map { CacheDiskStaticUtils.getString(key, "") }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe(consumer)
    }

    open fun putCacheDisk(key: String, content: String, time: Int) {
        Observable.just("保存缓存")
            .map {
                CacheDiskStaticUtils.put(key, content, time)
                "$key 缓存成功"
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
            .subscribe { LogUtils.d(it) }
    }

    /**
     * ------------- 提示框 -------------
     */
    fun showLoading(xPopupCallback: XPopupCallback? = null, msg: String? = "请稍候") {
        xPopup?.dismiss()

        xPopup = XPopup.Builder(activity)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .setPopupCallback(xPopupCallback).asLoading(msg)

        if (xPopup?.isDismiss == true) xPopup?.show()
    }

    fun showDialog(
        title: String? = "提示",
        content: String? = "",
        cancelTx: String? = "取消",
        confirmTx: String? = "确定",
        confirmLi: MyXPopupListener? = null,
        cancelLi: MyXPopupListener? = null,
        isHideCancel: Boolean = true,
        callback: XPopupCallback? = null
    ) {
        xPopup?.dismiss()

        xPopup = XPopup.Builder(activity).setPopupCallback(callback)
            .dismissOnBackPressed(false).dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel)

        xPopup?.show()
    }

    fun dismissDialog(finish: Boolean = false) {
        if (finish) {
            xPopup?.dismissWith { activity?.finish() }
        } else {
            xPopup?.dismiss()
        }
    }

    /**
     * ------------- 清理 -------------
     */
    override fun onDestroyView() {
        super.onDestroyView()
        dismissDialog()
    }

}