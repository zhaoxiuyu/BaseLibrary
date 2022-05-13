package com.base.app.sample.views

import com.base.app.R
import com.base.app.entitys.PageDescribe
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ViewsAdapter :
    BaseQuickAdapter<PageDescribe, BaseViewHolder>(R.layout.fragment_views_item) {

    override fun convert(holder: BaseViewHolder, item: PageDescribe) {
        holder.setText(R.id.material_textview_name, item.name)
        holder.setText(R.id.material_textview_describe, item.describe)
    }

}
