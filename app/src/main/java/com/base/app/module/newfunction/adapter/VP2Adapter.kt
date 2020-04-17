package com.base.app.module.newfunction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.app.R

class VP2Adapter(var mStrs: MutableList<String>) :
    RecyclerView.Adapter<VP2Adapter.PageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vp2, parent, false)
        return PageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mStrs.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bindView(mStrs[position])
    }

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView = itemView.findViewById(R.id.itemTv)
        fun bindView(str: String) {
            mTextView.text = str
        }
    }

}