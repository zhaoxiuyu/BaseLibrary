package com.base.module.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.module.view.ui.TabPage2Fragment

/**
 * 统计，ViewPage2适配器
 */
class StatisticsVp2Adapter(act: FragmentActivity, private val titles: MutableList<String>) :
    FragmentStateAdapter(act) {

    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment {
        return TabPage2Fragment.newInstance(titles[position])
    }

}