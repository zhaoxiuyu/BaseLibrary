package com.base.library.mvp.core

import android.content.Context
import com.base.library.interfaces.MyXPopupListener
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.XPopupCallback
import io.reactivex.rxjava3.functions.Consumer

/**
 * 作用: 基于MVP架构的View 视图基类
 */
interface VPView {

    fun showDialog(xPopupCallback: XPopupCallback? = null, loading: String? = "正在加载...")

    fun showDialog(
        title: String? = "提示",
        content: String? = "",
        cancelTx: String? = "取消",
        confirmTx: String? = "确定",
        confirmLi: OnConfirmListener? = null, // 确定按钮回调
        cancelLi: OnCancelListener? = null, // 取消按钮回调
        isHideCancel: Boolean? = true, // 是否隐藏取消按钮,
        callback: XPopupCallback? = null // 提示框 显示和隐藏监听
    )

    fun getDismissFinishListener(): MyXPopupListener

    fun disDialog(finish: Boolean = false)

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
