package com.base.library.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.base.library.databinding.BaseLayoutBinding
import com.base.library.interfaces.MyTitleBarListener
import com.base.library.interfaces.MyXPopListener
import com.base.library.mvvm.core.OnHandleCallback
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
    abstract fun registerObserve()

    // 最外层的布局，内部包裹添加进来的View
    private val bBind by lazy { BaseLayoutBinding.inflate(layoutInflater) }

    // 根View
    private var bRootView: View? = null

    // 是否使用沉浸式
    private var immersionBar = true

    val mApplication: BApplication by lazy { activity?.application as BApplication }
    private var xPopup: BasePopupView? = null

    // 加载提示框
    private var loadingPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initArgs(arguments)
        registerObserve()
        initView()
        return bRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        immersionBar()
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

    fun setContentView(rootView: View, immersionBar: Boolean = true) {
        this.bRootView = rootView
        this.immersionBar = immersionBar
    }

    fun setContentViewBar(rootView: View, title: Boolean = true, immersionBar: Boolean = true) {
        rootView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // 是否显示 顶部导航栏
        val isShow = if (title) View.VISIBLE else View.GONE
        bBind.titleBar.visibility = isShow

        // 沉浸式,把stateBar设置为状态栏的高度,用来延伸到状态栏
        if (immersionBar) {
            val stateBarLp = bBind.stateBar.layoutParams
            stateBarLp.height = BarUtils.getStatusBarHeight()
            bBind.stateBar.layoutParams = stateBarLp
        }

        bBind.root.removeView(rootView)
        bBind.root.addView(rootView)

        this.bRootView = bBind.root
        this.immersionBar = immersionBar
    }

    private fun immersionBar() {
        if (immersionBar) {
            UltimateBarX.with(this).fitWindow(false).light(true).applyStatusBar()
        }
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
            .show()
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
     * --------------------- 状态的回调
     */
    override fun onLoading(method: String, msg: String) {
        Log.v("OnHandleCallback", "onLoading")
        loadingPopup?.dismiss()
        loadingPopup = XPopup.Builder(requireActivity())
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asLoading(msg).show()
    }

    override fun onSuccess(method: String, msg: String, clickFinish: Boolean) {
        Log.v("OnHandleCallback", "onSuccess")
        dismissDialog()
        val mListener = getDismissFinish(clickFinish)
        showDialog(content = msg, confirmLi = mListener)
    }

    override fun onError(method: String, msg: String, clickFinish: Boolean) {
        Log.v("OnHandleCallback", "onError")
        dismissDialog()
        val mListener = getDismissFinish(clickFinish)
        showDialog(content = msg, confirmLi = mListener)
    }

    override fun onCompleted(method: String) {
        Log.v("OnHandleCallback", "onCompleted")
        loadingPopup?.dismiss()
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
        if (bRootView?.parent != null) {
            (bRootView?.parent as ViewGroup).removeView(bRootView)
        }
    }

}