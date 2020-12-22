package com.base.library.custom

import android.view.View
import com.billy.android.loading.Gloading

class GlobalAdapter : Gloading.Adapter {

    override fun getView(holder: Gloading.Holder?, convertView: View?, status: Int): View? {
        var mGlobalStatusView: GlobalStatusView? = null

        if (holder != null) {
            if (convertView != null && convertView is GlobalStatusView) {
                mGlobalStatusView = convertView
            }

            if (mGlobalStatusView == null) {
                mGlobalStatusView = GlobalStatusView(holder.context, holder.retryTask)
            }
        }
        mGlobalStatusView?.setStatus(status)
        return mGlobalStatusView
    }

}