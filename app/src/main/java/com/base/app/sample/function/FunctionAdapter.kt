package com.base.app.sample.function

import com.base.app.R
import com.base.app.entitys.PageDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class FunctionAdapter :
    BaseQuickAdapter<PageDescribe, BaseViewHolder>(R.layout.fragment_function_item) {

    override fun convert(holder: BaseViewHolder, item: PageDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}