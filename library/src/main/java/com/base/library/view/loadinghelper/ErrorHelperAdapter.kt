package com.base.library.view.loadinghelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.R
import com.dylanc.loadinghelper.LoadingHelper

/**
 * 错误的状态布局
 */
class ErrorHelperAdapter : LoadingHelper.Adapter<ErrorHelperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_error_helper, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        holder.tvErrorText.setOnClickListener {
            holder.onReloadListener?.onReload()
        }
    }

    class ViewHolder internal constructor(rootView: View) :
        LoadingHelper.ViewHolder(rootView) {
        var tvErrorText: View = rootView.findViewById(R.id.tv_error_text)
    }

}