package com.base.module.utils.adapter

import com.base.module.utils.R
import com.base.module.utils.entity.UtilsDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class UtilsAdapter :
    BaseQuickAdapter<UtilsDescribe, BaseViewHolder>(R.layout.fragment_utils_item) {

    override fun convert(holder: BaseViewHolder, item: UtilsDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}
