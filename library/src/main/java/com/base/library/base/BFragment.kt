package com.base.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.base.library.interfaces.MyXPopListener
import com.base.library.mvvm.OnHandleCallback
import com.base.library.util.ScreenUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

abstract class BFragment : Fragment(), OnHandleCallback {

    abstract fun initArgs(mArguments: Bundle?)
    abstract fun initView()
    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun registerObserve()

    // 根View
    private var bRootView: View? = null

    // 是否使用沉浸式
    private var immersion = true

    // 给指定 view 添加 padding
    private var topPadding: View? = null

    val mApplication: BApplication by lazy { activity?.application as BApplication }

    // 加载提示框
    private var loadingPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initParadigm()
        initArgs(arguments)
        registerObserve()
        initView()
        return bRootView
    }

    open fun initParadigm() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (immersion) {
            immersionBar()
        }
        topPadding?.let {
            addStatusBarTopPadding(topPadding)
        }

        initData(savedInstanceState)
    }

    fun setContentView(rootView: View, immersion: Boolean = true, topPadding: View? = null) {
        this.bRootView = rootView
        this.immersion = immersion
        this.topPadding = topPadding
    }

    fun immersionBar() {
        UltimateBarX.with(this)
            // 布局是否侵入状态栏
            .fitWindow(false)
            // light模式 状态栏字体 true: 灰色，false: 白色 Android 6.0+
            // light模式 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
            .light(true)
            // 状态栏透明效果
            .transparent()
            // 应用到状态栏
            .applyStatusBar()
    }

    fun addStatusBarTopPadding(topPadding: View? = null) {
        ScreenUtils.addStatusBarTopPadding(topPadding)
    }

    /**
     * ------------- 提示框 -------------
     */
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
        XPopup.Builder(activity).setPopupCallback(callback)
            .dismissOnBackPressed(false).dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel)
            .show()
    }

//    // 提供一个接口,关闭 Dialog 的同时是否关闭页面
//    fun getDismissFinish(isFinish: Boolean = false, runnable: Runnable? = null): MyXPopListener =
//        object : MyXPopListener {
//            override fun onDis() {
//                dismissDialog(isFinish, runnable)
//            }
//        }
//
//    // 关闭 Dialog
//    fun dismissDialog(isFinish: Boolean = false, runnable: Runnable? = null) {
//        xPopup?.dismissWith {
//            runnable?.run()
//            if (isFinish) {
//                activity?.finish()
//            }
//        }
//    }

    override fun loadingEvent(method: String, msg: String) {
        dismissEvent()
        loadingPopup = XPopup.Builder(activity)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asLoading(msg).show()
    }

    override fun messageEvent(method: String, msg: String, finish: Boolean) {
        showDialog(content = msg, confirmLi = object : MyXPopListener {
            override fun onDis() {
                if (finish) activity?.finish()
            }
        })
    }

    override fun dismissEvent(method: String) {
        loadingPopup?.dismiss()
        loadingPopup = null
    }

    override fun finishEvent() {
        activity?.finish()
    }

    override fun startActivityEvent() {
    }

    override fun otherEvent(content: String) {
    }

    /**
     * ------------- 清理 -------------
     */
    override fun onDestroyView() {
        super.onDestroyView()
        if (bRootView?.parent != null) {
            (bRootView?.parent as ViewGroup).removeView(bRootView)
        }
    }

}