package com.base.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.base.library.interfaces.MyXPopListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.java.UltimateBarX

abstract class BFragment : Fragment() {
    //    , OnHandleCallback
    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?, mBundle: Bundle?)
    abstract fun initArgs(mArguments: Bundle?)
    abstract fun getRootView(): View
    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun registerObserve()

    private var mContentView: View? = null

    // 是否使用沉浸式
    private var immersion = true

    val mApplication: BApplication by lazy { activity?.application as BApplication }

    // 加载提示框
    private var loadingPopup: BasePopupView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewBinding(inflater, container, savedInstanceState)
        mContentView = getRootView()
        initArgs(arguments)
        registerObserve()
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
    }

    fun setContentView(contentView: View, topPadding: View? = null) {
        this.mContentView = contentView
        addStatusBarTopPadding(topPadding?.let { topPadding } ?: let { contentView })
    }

    fun setUltimateBarX() {
        UltimateBarX
            .statusBar(this)
            // 布局是否侵入状态栏
            .fitWindow(false)
            // light模式 状态栏字体 true: 灰色，false: 白色 Android 6.0+
            // light模式 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
            .light(true)
            // 状态栏透明效果
            .transparent()
            // 应用到状态栏
            .apply()
    }

    fun addStatusBarTopPadding(barTopPadding: View? = null) {
//        ScreenUtils.addStatusBarTopPadding(topPadding)
        barTopPadding?.addStatusBarTopPadding()
    }

    /**
     * ------------- 提示框 -------------
     */
    fun showDialog(
        title: String? = "提示",
        content: String? = "暂无内容",
        cancelTx: String? = "取消",
        confirmTx: String? = "确定",
        confirmLi: MyXPopListener? = null,
        cancelLi: MyXPopListener? = null,
        isHideCancel: Boolean = true,
        callback: XPopupCallback? = null
    ): BasePopupView {
        return XPopup.Builder(activity).setPopupCallback(callback)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel)
            .show()
    }

    fun showMessage(msg: String, finish: Boolean = false) {
        showDialog(content = msg, confirmLi = object : MyXPopListener {
            override fun onDis() {
                if (finish) activity?.finish()
            }
        })
    }

    fun showLoading(msg: String = "请稍后") {
        if (loadingPopup?.isDismiss != false) {
            loadingPopup = XPopup.Builder(activity)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asLoading(msg).show()
        }
    }

    fun dismissLoading() {
        loadingPopup?.dismiss()
        loadingPopup = null
    }

    override fun onDestroyView() {
        dismissLoading()
        super.onDestroyView()
    }

}