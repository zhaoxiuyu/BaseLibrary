package com.base.app.module.views.tablayout.ui

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.base.app.databinding.ActivityTabLayoutBinding
import com.base.app.module.views.tablayout.adapter.StatisticsVp2Adapter
import com.base.library.base.BActivity
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutActivity : BActivity() {

    private val titles = mutableListOf("概况", "日租房", "钟点房", "出租率", "渠道")

    private val mBind by lazy { ActivityTabLayoutBinding.inflate(layoutInflater) }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData(savedInstanceState: Bundle?) {
        titles.forEach {
            mBind.tabLayout.addTab(mBind.tabLayout.newTab().setText(it))
        }

        // 设置 Fragment 适配器
        val vp2Adapter = StatisticsVp2Adapter(this, titles)
        mBind.viewPager2.adapter = vp2Adapter
//        mBind.viewPager2.offscreenPageLimit = 3
        mBind.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        // TabLayout 和 VP2 绑定
        TabLayoutMediator(mBind.tabLayout, mBind.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()

    }

    override fun initObserve(): Nothing? = null

}