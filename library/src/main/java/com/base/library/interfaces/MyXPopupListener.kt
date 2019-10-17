package com.base.library.interfaces

import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener

interface MyXPopupListener : OnConfirmListener, OnCancelListener {

    // 确定
    override fun onConfirm() {
        onDis()
    }

    // 取消
    override fun onCancel() {
        onDis()
    }

    fun onDis()

}