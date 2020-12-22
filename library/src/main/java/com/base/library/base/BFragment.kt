package com.base.library.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.base.library.databinding.BaseFragmentBinding
import com.base.library.interfaces.MyXPopupListener
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.core.OnHandleCallback
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.LogUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.rxjava.rxlife.lifeOnMain
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import rxhttp.wrapper.entity.Progress

abstract class BFragment : Fragment(), OnHandleCallback {

    abstract fun initArgs(bundle: Bundle?)
    abstract fun initData(bundle: Bundle?)
    abstract fun initView(): View
    abstract fun initObserve(): MutableList<BViewModel>?

    private val bBind by lazy { BaseFragmentBinding.inflate(layoutInflater) }

    val mApplication: BApplication by lazy { activity?.application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initArgs(arguments)
        initObserve()?.forEach { bViewModel ->
            bViewModel.getState().observe(viewLifecycleOwner, Observer { state ->
                state.handler(this)
            })
        }
        return initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
    }

    /**
     * --------------------- 通用的 TitleBar，避免每个 Fragment 都复写一遍 ---------------------
     * 使用如下 ：  override fun initView() = setContentViewBar(mBind.root)
     */
    fun getTitleBar() = bBind.titleBar

    fun setContentViewBar(view: View): View {
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp
        bBind.root.addView(view)
        return bBind.root
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
     * 状态的回调
     */
    override fun onLoading(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onLoading")
        if (mRequest.showLoading) {
            showLoading(null, mRequest.msg)
        }
    }

    override fun onSuccess(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onSuccess")
        // 网络请求是否弹出加载框，就对应的关闭
        if (mRequest.showLoading) dismissDialog()
        // 请求成功，是否弹窗提示
        if (mRequest.showSuccess) {
            val mListener = getDismissFinish(mRequest.successClickFinish)
            showDialog(content = mRequest.msg, confirmLi = mListener)
        }
    }

    override fun onError(mRequest: RxRequest) {
        Log.v("OnHandleCallback", "onError")
        // 网络请求是否弹出加载框，就对应的关闭
        if (mRequest.showLoading) dismissDialog()
        // 请求失败，是否弹窗提示
        if (mRequest.showFail) {
            val mListener = getDismissFinish(mRequest.failClickFinish)
            showDialog(content = mRequest.msg, confirmLi = mListener)
        }
    }

    override fun onCompleted() {
        Log.v("OnHandleCallback", "onCompleted")
    }

    override fun onProgress(progress: Progress?) {
        Log.v("OnHandleCallback", "onProgress")
    }

    /**
     * ------------- 清理 -------------
     */
    override fun onDestroyView() {
        super.onDestroyView()
        dismissDialog()
    }

}