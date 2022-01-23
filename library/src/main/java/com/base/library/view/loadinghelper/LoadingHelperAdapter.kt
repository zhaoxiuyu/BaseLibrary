package com.base.library.view.loadinghelper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadingstateview.LoadingStateView

/**
 * 加载中的状态布局
 */
class LoadingHelperAdapter : LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): LoadingStateView.ViewHolder {
        return LoadingStateView.ViewHolder(
            inflater.inflate(R.layout.layout_loading_helper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingStateView.ViewHolder) {
        val lp = holder.rootView.layoutParams
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        holder.rootView.layoutParams = lp
    }

}