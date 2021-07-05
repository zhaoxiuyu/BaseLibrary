package com.base.module.function.adapter

import com.base.module.function.R
import com.base.module.function.entity.FunctionDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class FunctionAdapter :
    BaseQuickAdapter<FunctionDescribe, BaseViewHolder>(R.layout.fragment_function_item) {

    override fun convert(holder: BaseViewHolder, item: FunctionDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}