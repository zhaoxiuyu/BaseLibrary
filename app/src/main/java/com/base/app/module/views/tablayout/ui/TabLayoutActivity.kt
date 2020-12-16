package com.base.app.module.views.tablayout.ui

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.base.app.databinding.ActivityTabLayoutBinding
import com.base.app.module.views.tablayout.adapter.StatisticsVp2Adapter
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.tabs.TabLayoutMediator

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

    private val mBind by lazy { ActivityTabLayoutBinding.inflate(layoutInflater) }

    override fun initArgs(intent: Intent?) = null

    override fun initView() {
        setContentView(mBind.root)
    }

    override fun initData() {
        titles.forEach {
            mBind?.let { bind ->
                bind.tabLayout.addTab(bind.tabLayout.newTab().setText(it))
            }
        }

        // 设置 Fragment 适配器
        val vp2Adapter = StatisticsVp2Adapter(this, fragments)
        mBind?.viewPager2?.adapter = vp2Adapter
        mBind?.viewPager2?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                LogUtils.d(position)
            }
        })

        mBind?.let { bind ->
            // TabLayout 和 VP2 绑定
            TabLayoutMediator(bind?.tabLayout, bind?.viewPager2,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    tab.text = titles[position]
                }).attach()
        }

    }

}