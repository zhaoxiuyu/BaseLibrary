package com.base.library.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.base.library.databinding.BaseLayoutBinding
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.interfaces.MyXPopListener
import com.base.library.mvvm.core.BViewModel
import com.base.library.mvvm.core.OnHandleCallback
import com.base.library.rxhttp.RxRequest
import com.blankj.utilcode.util.BarUtils
import com.hjq.bar.TitleBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.zackratos.ultimatebarx.library.UltimateBarX
import rxhttp.wrapper.entity.Progress

abstract class BFragment : Fragment(), OnHandleCallback {

    abstract fun initArgs(mArguments: Bundle?)
    abstract fun initView()
    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initObserve(): MutableList<BViewModel>?

    private val bBind by lazy { BaseLayoutBinding.inflate(layoutInflater) }
    private var bView: View? = null

    // 是否使用沉浸式
    private var immersionBar = true

    val mApplication: BApplication by lazy { activity?.application as BApplication }
    private var xPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initArgs(arguments)

        initObserve()?.forEach { bViewModel ->
            bViewModel.getState()?.observe(viewLifecycleOwner, Observer { state ->
                state.handler(this)
            })
        }

        initView()

        return bView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (immersionBar) immersionBar()
        initData(savedInstanceState)
    }

    /**
     * --------------------- 通用的 TitleBar，避免每个 Fragment 都复写一遍 ---------------------
     */
    fun getTitleBar() = bBind.titleBar

    /**
     * 默认有返回功能，如果不要返回 传listener实例 空实现就可以了。
     */
    fun setTitleBarOperation(
        title: String,
        listener: MyTitleBarListener? = null,
    ): TitleBar {
        getTitleBar().title = title
        if (listener == null) {
            getTitleBar().setOnTitleBarListener(object : MyTitleBarListener() {
                override fun onLeftClick(v: View?) {
                    requireActivity().finish()
                }
            })
        } else {
            getTitleBar().setOnTitleBarListener(listener)
        }
        return getTitleBar()
    }

    fun setContentView(view: View) {
        this.bView = view
    }

    fun setContentViewBar(view: View, immersionBar: Boolean = true) {
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp

        this.bView = bBind.root
        this.immersionBar = immersionBar

        bBind.root.addView(view)
    }

    fun immersionBar() {
        val stateBarLp = bBind.stateBar.layoutParams
        stateBarLp.height = BarUtils.getStatusBarHeight()
        bBind.stateBar.layoutParams = stateBarLp

        UltimateBarX.with(this).fitWindow(false).light(true).applyStatusBar()
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
        confirmLi: MyXPopListener? = null,
        cancelLi: MyXPopListener? = null,
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
    fun getDismissFinish(isFinish: Boolean = false, runnable: Runnable? = null): MyXPopListener =
        object : MyXPopListener {
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
    override fun onLoading(method: String, msg: String) {
        Log.v("OnHandleCallback", "onLoading")
        showLoading(null, msg)
//        if (mRequest.showLoading) {
//            showLoading(null, mRequest.msg)
//        }
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

    override fun onCompleted(method: String) {
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