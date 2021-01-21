package com.base.app.module.demos.adapter

import com.base.app.R
import com.base.app.entitys.UtilsDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import javax.inject.Inject

class FragmentAdapter @Inject constructor() :
    BaseQuickAdapter<UtilsDescribe, BaseViewHolder>(R.layout.fragment_utils_item) {

    override fun convert(holder: BaseViewHolder, item: UtilsDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}