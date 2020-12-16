package com.base.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.base.library.interfaces.MyXPopupListener
import com.base.library.mvvm.core.VMViewModel
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.rxjava.rxlife.lifeOnMain
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * ImmersionOwner 用来在Fragment中实现沉浸式
 */
abstract class BFragment : Fragment() {

    abstract fun initArgs(bundle: Bundle?): VMViewModel?
    abstract fun initData(bundle: Bundle?)
    abstract fun initView(): View

    var vm: VMViewModel? = null

    val mApplication: BApplication by lazy { activity?.application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = initArgs(arguments)
        return initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
    }

    /**
     * ------------- 文件缓存 -------------
     */
    open fun getCacheDisk(key: String, consumer: Consumer<String>) {
        Observable.just("获取缓存").map { CacheDiskStaticUtils.getString(key, "") }
            .subscribeOn(Schedulers.io()).lifeOnMain(this).subscribe(consumer)
    }

    open fun putCacheDisk(key: String, content: String, time: Int) {
        Observable.just("保存缓存")
            .map {
                CacheDiskStaticUtils.put(key, content, time)
                "$key 缓存成功"
            }
            .subscribeOn(Schedulers.io()).lifeOnMain(this).subscribe { LogUtils.d(it) }
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

    // 提供一个接口,关闭 Dialog 的同时是否关闭页面
    fun getDismissFinish(isFinish: Boolean = false, runnable: Runnable? = null): MyXPopupListener =
        object : MyXPopupListener {
            override fun onDis() {
                dismissDialog(isFinish, runnable)
            }
        }

    // 关闭 Dialog
    fun dismissDialog(isFinish: Boolean = false, runnable: Runnable? = null) {
        xPopup?.dismissWith {
            runnable?.run()
            if (isFinish) {
                activity?.finish()
            }
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