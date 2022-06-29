package com.base.library.view.loadingstateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

/**
 * 加载中的状态布局
 */
class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.layout_loading_state_view, parent, false)

    fun updateMessage() {

    }

}