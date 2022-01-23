package com.base.library.view.loadinghelper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadingstateview.LoadingStateView

/**
 * 空数据的状态布局
 */
class EmptyHelperAdapter : LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): LoadingStateView.ViewHolder {
        return LoadingStateView.ViewHolder(
            inflater.inflate(R.layout.layout_empty_helper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingStateView.ViewHolder) {

    }

}