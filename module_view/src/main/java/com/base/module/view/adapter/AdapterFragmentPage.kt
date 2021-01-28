package com.base.module.view.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.library.base.BFragment
import com.base.module.view.ui.ViewPage2Fragment1
import com.base.module.view.ui.ViewPage2Fragment2
import com.base.module.view.ui.ViewPage2Fragment3

class AdapterFragmentPage(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = SparseArray<BFragment>()

    init {
        fragments.put(0, ViewPage2Fragment1())
        fragments.put(1, ViewPage2Fragment2())
        fragments.put(2, ViewPage2Fragment3())
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}