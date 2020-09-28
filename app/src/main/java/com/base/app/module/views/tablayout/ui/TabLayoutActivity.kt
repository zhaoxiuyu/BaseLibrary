package com.base.app.module.views.tablayout.ui

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.base.app.R
import com.base.app.module.views.tablayout.adapter.StatisticsVp2Adapter
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayoutActivity : VMActivity() {

    private val titles = mutableListOf("概况", "日租房", "钟点房", "出租率", "渠道")
    private val fragments by lazy {
        mutableListOf(
            TabLayoutFragment.newInstance("概况"),
            TabLayoutFragment.newInstance("日租房"),
            TabLayoutFragment.newInstance("钟点房"),
            TabLayoutFragment.newInstance("出租率"),
            TabLayoutFragment.newInstance("渠道")
        )
    }

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentView(R.layout.activity_tab_layout)
    }

    override fun lazyData() {
        titles.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }

        // 设置 Fragment 适配器
        val vp2Adapter = StatisticsVp2Adapter(this, fragments)
        view_pager2.adapter = vp2Adapter
        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                LogUtils.d(position)
            }
        })

        // TabLayout 和 VP2 绑定
        TabLayoutMediator(tab_layout, view_pager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()
    }

}