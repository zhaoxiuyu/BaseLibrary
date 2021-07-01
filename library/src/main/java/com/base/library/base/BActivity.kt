package com.base.library.base

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.base.library.interfaces.MyXPopListener
import com.base.library.mvvm.core.OnHandleCallback
import com.base.library.util.ScreenUtils
import com.blankj.utilcode.util.AdaptScreenUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import rxhttp.wrapper.entity.Progress

abstract class BActivity : AppCompatActivity(), OnHandleCallback {

    abstract fun initArgs(mIntent: Intent?)
    abstract fun initView()
    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun registerObserve()

    val mApplication: BApplication by lazy { application as BApplication }
    private var xPopup: BasePopupView? = null

    // 加载提示框
    private var loadingPopup: BasePopupView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParadigm()
        initArgs(intent)
        initView()
        // window.decorView 获取到DecorView后,调用post方法,此时DecorView的attachInfo为空,
        // 会将这个Runnable放置runQueue中。runQueue内的任务会在ViewRootImpl.performTraversals的开始阶段被依次取出执行,
        // 这个方法内会执行到DecorView的测量、布局、绘制操作，不过runQueue的执行顺序会在这之前,所以需要再进行一次post操作
        // window.decorView.post { mHandler.post { initData() } }
        // IdleHandler在线程处于空闲的时候被执行,false 该回调进行移除,true 以后会多次调用
        Looper.myQueue().addIdleHandler {
            initData(savedInstanceState)
            false
        }
    }

    open fun initParadigm() {
    }

    /**
     * 给 ContentView 的外面添加一个 通用的顶部导航栏
     */
    fun setContentViewBar(rootView: View, immersion: Boolean = true, topPadding: View? = null) {
        setContentView(rootView)

        if (immersion) {
            immersionBar()
        }

        // 给根布局添加一个状态栏高度的 padding
        topPadding?.let {
            ScreenUtils.addStatusBarTopPadding(topPadding)
        }
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

    /**
     * --------------------- 获取新的值需要重新 setIntent
     */
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        setIntent(intent)
//    }

    /**
     * --------------------- 用来做屏幕适配用的
     */
    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }

    /**
     * --------------------- 提示框
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
    ) {
        xPopup?.dismiss()

        xPopup = XPopup.Builder(this).setPopupCallback(callback)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asConfirm(title, content, cancelTx, confirmTx, confirmLi, cancelLi, isHideCancel)
            .show()
    }

    fun getDismissFinish(isFinish: Boolean, runnable: Runnable? = null) = object : MyXPopListener {
        override fun onDis() {
            dismissDialog(isFinish, runnable)
        }
    }

    fun dismissDialog(isFinish: Boolean = false, runnable: Runnable? = null) {
        xPopup?.dismissWith {
            runnable?.run()
            if (isFinish) finish()
        }
    }

    /**
     * --------------------- 状态的回调
     */
    override fun onLoading(method: String, msg: String) {
        Log.d("OnHandleCallback", "onLoading")
        loadingPopup?.dismiss()
        loadingPopup = XPopup.Builder(this)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asLoading(msg).show()
    }

    override fun onSuccess(method: String, msg: String, clickFinish: Boolean) {
        Log.d("OnHandleCallback", "onSuccess")
        dismissDialog()
        val mListener = getDismissFinish(clickFinish)
        showDialog(content = msg, confirmLi = mListener)
    }

    override fun onError(method: String, msg: String, clickFinish: Boolean) {
        Log.d("OnHandleCallback", "onError")
        dismissDialog()
        val mListener = getDismissFinish(clickFinish)
        showDialog(content = msg, confirmLi = mListener)
    }

    override fun onCompleted(method: String) {
        Log.d("OnHandleCallback", "onCompleted")
        loadingPopup?.dismiss()
    }

    override fun onProgress(progress: Progress?) {
        Log.d("OnHandleCallback", "onProgress")
    }

    /**
     * --------------------- 结束,清理
     */
    override fun onDestroy() {
        loadingPopup?.dismiss()
        dismissDialog(false)
        super.onDestroy()
    }

}