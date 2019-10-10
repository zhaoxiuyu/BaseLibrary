package com.base.library.mvp

import android.content.Context
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.XPopupCallback
import io.reactivex.functions.Consumer

/**
 * 作用: 基于MVP架构的View 视图基类
 */
interface BView {

    fun showDialog(xPopupCallback: XPopupCallback? = null, loading: String? = "正在加载...")

    fun showDialog(
        title: String? = "提示",
        content: String? = "",
        cancelBtnText: String? = "取消",
        confirmBtnText: String? = "确定",
        confirmListener: OnConfirmListener? = null, // 确定按钮回调
        cancelListener: OnCancelListener? = null, // 取消按钮回调
        xPopupCallback: XPopupCallback? = null, // 提示框 显示和隐藏监听
        isHideCancel: Boolean = true // 是否隐藏取消按钮
    )

    fun getConfirmDisListener(): OnConfirmListener
    fun getConfirmFinishListener(): OnConfirmListener

    fun getCancelDisListener(): OnCancelListener
    fun getCancelFinishListener(): OnCancelListener

    fun disDialog() // 销毁对话框

    fun dismissWith() // 销毁对话框 之后 关闭页面

    /**
     * Activity获取当前this，Fragment获取getActivity
     */
    fun getContext(): Context

    /**
     * 添加 获取 缓存
     */
    fun putCache(key: String, content: String, time: Int = -1)

    fun getCache(key: String, consumer: Consumer<String>)

    /**
     * 可以用来保存日志
     */
    fun other(content: String, behavior: String, level: String)

}
