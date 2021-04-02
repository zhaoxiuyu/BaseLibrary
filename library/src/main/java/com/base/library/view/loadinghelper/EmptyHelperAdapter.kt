package com.base.library.view.loadinghelper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 空数据的状态布局
 */
class EmptyHelperAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater, parent: ViewGroup
    ): LoadingHelper.ViewHolder {
        return LoadingHelper.ViewHolder(
            inflater.inflate(R.layout.layout_empty_helper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {

    }

}