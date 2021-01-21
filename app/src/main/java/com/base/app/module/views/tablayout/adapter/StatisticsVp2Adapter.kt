package com.base.app.module.views.tablayout.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.app.module.views.tablayout.ui.TabPage2ChildFragment

/**
 * 统计，ViewPage2适配器
 */
class StatisticsVp2Adapter(fragment: Fragment, private val titles: MutableList<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment {
        return TabPage2ChildFragment.newInstance(titles[position])
    }

}