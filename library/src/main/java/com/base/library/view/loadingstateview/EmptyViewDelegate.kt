package com.base.library.view.loadingstateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

/**
 * 空数据的状态布局
 */
class EmptyViewDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.layout_empty_state_view, parent, false)

}