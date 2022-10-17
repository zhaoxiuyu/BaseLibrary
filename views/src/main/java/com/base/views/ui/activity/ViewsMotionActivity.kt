package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.base.library.mvvm.VMActivity
import com.base.views.databinding.ViewsActivityMotionBinding
import com.base.views.ui.fragment.ViewsMotionFragment1
import com.base.views.ui.fragment.ViewsMotionFragment2

/**
 * MotionLayout 动画
 * 参考链接如下：https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class ViewsMotionActivity : VMActivity<ViewsActivityMotionBinding>() {

    override fun initArgs(mIntent: Intent?) {
    }

    override fun initView() {
        setContentViewBar(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewBinding.vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewBinding.vp2.adapter = MotionLayoutFragmentPage(this) // 使用 Fragment
    }

    override fun registerObserve() {
    }

}

class MotionLayoutFragmentPage(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = SparseArray<Fragment>()

    init {
        fragments.put(0, ViewsMotionFragment1.newInstance(0))
        fragments.put(1, ViewsMotionFragment2.newInstance(1))
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}

