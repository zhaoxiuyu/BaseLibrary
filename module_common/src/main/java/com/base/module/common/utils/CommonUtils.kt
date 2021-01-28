package com.base.module.common.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.module.common.R
import com.ckr.decoration.DividerLinearItemDecoration

object CommonUtils {

    /**
     * RecyclerView线性分割线,高度0.3,去掉头尾分割线
     */
    fun getDividerLinear(mContext: Context): DividerLinearItemDecoration {
        val builder = DividerLinearItemDecoration.Builder(mContext, LinearLayoutManager.VERTICAL)
        builder.setDivider(R.drawable.bg_divider_demos_recyclerview)
        builder.removeHeaderDivider(true)
        builder.removeFooterDivider(true)
        return builder.build()
    }

}