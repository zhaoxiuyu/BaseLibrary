package com.base.library.view.loadingstateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.base.library.R
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

/**
 * 错误的状态布局
 */
class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_error_state_view, parent, false)

        val tvErrorText = view.findViewById<TextView>(R.id.tv_error_text)
        tvErrorText.setOnClickListener {
            onReloadListener?.onReload()
        }

        return view
    }

}