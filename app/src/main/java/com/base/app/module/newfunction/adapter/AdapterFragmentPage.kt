package com.base.app.module.newfunction.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.base.app.module.newfunction.ui.Fragment1
import com.base.app.module.newfunction.ui.Fragment2
import com.base.app.module.newfunction.ui.Fragment3
import com.base.library.base.BFragment

class AdapterFragmentPage(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = SparseArray<BFragment>()

    init {
        fragments.put(0, Fragment1())
        fragments.put(1, Fragment2())
        fragments.put(2, Fragment3())
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}