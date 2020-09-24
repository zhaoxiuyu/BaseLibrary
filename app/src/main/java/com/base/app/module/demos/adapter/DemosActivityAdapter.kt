package com.base.app.module.demos.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter

class DemosActivityAdapter : BannerAdapter<String, DemosActivityAdapter.BannerViewHolder>(null) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ImageView(parent?.context)
        )
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
//        holder?.imageView?.setImageResource()
    }

    class BannerViewHolder(@NonNull view: ImageView) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView = view

    }

}