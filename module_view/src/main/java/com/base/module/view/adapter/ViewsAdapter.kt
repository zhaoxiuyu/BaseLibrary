package com.base.module.view.adapter

import com.base.module.view.R
import com.base.module.view.entity.ViewsDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ViewsAdapter :
    BaseQuickAdapter<ViewsDescribe, BaseViewHolder>(R.layout.fragment_views_item) {

    override fun convert(holder: BaseViewHolder, item: ViewsDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}
