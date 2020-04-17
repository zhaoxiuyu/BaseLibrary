package com.base.app.module.newfunction.ui

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.base.app.R
import com.base.app.module.newfunction.adapter.AdapterFragmentPage
import com.base.app.module.newfunction.adapter.VP2Adapter
import com.base.app.module.newfunction.transformer.ScaleInTransformer
import com.base.app.module.newfunction.viewmodel.FunctionViewModel
import com.base.app.utils.TestDatas
import com.base.library.base.VMActivity
import kotlinx.android.synthetic.main.activity_viewpage2.*

class ViewPage2 : VMActivity<FunctionViewModel>() {

    private val vP2Adapter by lazy { VP2Adapter(TestDatas.getStrs(5)) }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_viewpage2)

        butVertical.setOnClickListener { vp2.orientation = ViewPager2.ORIENTATION_VERTICAL }
        butHorizontal.setOnClickListener { vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL }

        // 模拟拖拽,想前一个页面滑动 -300f
        butDrag.setOnClickListener {
            vp2.beginFakeDrag()
            if (vp2.fakeDragBy(-300f)) {
                vp2.endFakeDrag()
            }
        }

//        vp2.isUserInputEnabled = false // 禁止 ViewPage2 滑动
    }

    override fun initData() {
//        vp2.adapter = vP2Adapter // 和 RecyclerView 的适配器一样使用
        vp2.adapter = AdapterFragmentPage(this) // 使用 Fragment

        // 实现一屏多页的效果,通过 RecyclerView 设置 Padding 来实现
        vp2.apply {
            // 在VP2中,这个属性默认为-1,不会加载两边的Fragment,默认懒加载,
            // 可以设置预加载多少个Fragment,但只会执行到onStart(),只有可见时才会执行到onResume(),onResume中判断是否第一次加载数据就可以了
            // 如果Fragment数量多,由于缓存大小原因,来回滑动会频繁创建销毁Fragment,这个参数可以直接设置为 fragments.size()
//            offscreenPageLimit = 1
            val rv = getChildAt(0) as RecyclerView
            rv.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_20)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }

        // 这是个 List 集合，可以组合各种 Transformer
        val cpt = CompositePageTransformer()
        cpt.addTransformer(MarginPageTransformer(10))  // 设置页面的间距
        cpt.addTransformer(ScaleInTransformer()) // 切换效果

        vp2.setPageTransformer(cpt)

        // 滑动事件监听根据需要重写,OnPageChangeCallback是个抽象类
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }
}