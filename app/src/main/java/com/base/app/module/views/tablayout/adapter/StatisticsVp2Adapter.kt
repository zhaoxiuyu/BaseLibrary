package com.base.app.module.views.tablayout.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.app.module.views.tablayout.ui.TabLayoutFragment

/**
 * 统计，ViewPage2适配器
 */
class StatisticsVp2Adapter(
    fa: FragmentActivity,
    private val fragments: MutableList<TabLayoutFragment>
) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}