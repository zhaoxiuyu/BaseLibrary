package com.base.app.sample.views.activity

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.base.app.databinding.ActivityMotionDemoBinding
import com.base.library.mvvm.VMActivity

/**
 * MotionLayout 动画
 * 参考链接如下：https://mp.weixin.qq.com/s/3IAPd53rMOrLiIUDT520-w
 */
class MotionLayoutActivity : VMActivity<ActivityMotionDemoBinding>() {

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
        fragments.put(0, MotionLayout1.newInstance(0))
        fragments.put(1, MotionLayout2.newInstance(1))
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}

