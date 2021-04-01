package com.base.library.custom.loadinghelper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 加载中的状态布局
 */
class LoadingHelperAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): LoadingHelper.ViewHolder {
        return LoadingHelper.ViewHolder(
            inflater.inflate(R.layout.layout_loading_helper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {
        val lp = holder.rootView.layoutParams
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        holder.rootView.layoutParams = lp
    }

}