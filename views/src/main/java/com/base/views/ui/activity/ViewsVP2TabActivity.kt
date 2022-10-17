package com.base.views.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.base.library.mvvm.VMActivity
import com.base.views.databinding.ViewsActivityVp2TabBinding
import com.base.views.ui.fragment.ViewsVP2TabFragment
import com.google.android.material.tabs.TabLayoutMediator

/**
 * TabLayout ViewPage2 使用
 */
class ViewsVP2TabActivity : VMActivity<ViewsActivityVp2TabBinding>() {

    private val titles = mutableListOf("概况", "日租房", "钟点房", "出租率", "渠道")

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(viewBinding.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        titles.forEach {
            viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText(it))
        }

        // 设置 Fragment 适配器
        val vp2Adapter = StatisticsVp2Adapter(this, titles)
        viewBinding.viewPager2.adapter = vp2Adapter

        viewBinding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        // TabLayout 和 VP2 绑定
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()

    }

    override fun registerObserve() {
    }

}


/**
 * 统计，ViewPage2适配器
 */
class StatisticsVp2Adapter(act: FragmentActivity, private val titles: MutableList<String>) :
    FragmentStateAdapter(act) {

    override fun getItemCount(): Int = titles.size

    override fun createFragment(position: Int): Fragment {
        return ViewsVP2TabFragment.newInstance(titles[position])
    }

}