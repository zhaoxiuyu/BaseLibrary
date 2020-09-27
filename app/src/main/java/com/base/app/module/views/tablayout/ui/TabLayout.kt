package com.base.app.module.views.tablayout.ui

import android.content.Intent
import com.base.app.R
import com.base.app.module.views.tablayout.adapter.StatisticsVp2Adapter
import com.base.library.mvvm.core.VMActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayout : VMActivity() {

    private val titles = mutableListOf("概况", "日租房", "钟点房", "出租率", "渠道1", "渠道2", "渠道3")
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
        super.initView()
        setContentView(R.layout.activity_tab_layout)
    }

    override fun lazyData() {
        titles.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }

        // 设置 Fragment 适配器
        val vp2Adapter = StatisticsVp2Adapter(this, fragments)
        view_pager2.adapter = vp2Adapter

        // TabLayout 和 VP2 绑定
        TabLayoutMediator(tab_layout, view_pager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()
    }

}