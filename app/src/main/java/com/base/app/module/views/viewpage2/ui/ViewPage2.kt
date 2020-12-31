package com.base.app.module.views.viewpage2.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.base.app.R
import com.base.app.base.MyConstant
import com.base.app.databinding.ActivityViewpage2Binding
import com.base.app.module.views.viewpage2.adapter.AdapterFragmentPage
import com.base.app.module.views.viewpage2.adapter.VP2Adapter
import com.base.app.module.views.viewpage2.transformer.ScaleInTransformer
import com.base.app.utils.MethodDatas
import com.base.library.base.BActivity
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.LogUtils

class ViewPage2 : BActivity() {

    private val mBind by lazy { ActivityViewpage2Binding.inflate(layoutInflater) }

    private val vP2Adapter by lazy {
        VP2Adapter(
            MethodDatas.getStrs(5)
        )
    }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(mBind.root)

        mBind.butVertical.setOnClickListener {
            mBind.vp2.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
        mBind.butHorizontal.setOnClickListener {
            mBind.vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        // 模拟拖拽,想前一个页面滑动 -300f
        mBind.butDrag.setOnClickListener {
            mBind.vp2.beginFakeDrag()
            if (mBind.vp2.fakeDragBy(-300f)!!) {
                mBind.vp2.endFakeDrag()
            }
        }

//        vp2.isUserInputEnabled = false // 禁止 ViewPage2 滑动
    }

    override fun initData(savedInstanceState: Bundle?) {
//        vp2.adapter = vP2Adapter // 和 RecyclerView 的适配器一样使用
        mBind.vp2.adapter =
            AdapterFragmentPage(this) // 使用 Fragment

        // 实现一屏多页的效果,通过 RecyclerView 设置 Padding 来实现
        mBind.vp2.apply {
            // 在VP2中,这个属性默认为-1,不会加载两边的Fragment,默认懒加载,
            // 可以设置预加载多少个Fragment,但只会执行到onStart(),只有可见时才会执行到onResume(),onResume中判断是否第一次加载数据就可以了
            // 如果Fragment数量多,由于缓存大小原因,来回滑动会频繁创建销毁Fragment,这个参数可以直接设置为 fragments.size()
//            offscreenPageLimit = 1
            val rv = getChildAt(0) as RecyclerView
            rv.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_10)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }

        // 这是个 List 集合，可以组合各种 Transformer
        val cpt = CompositePageTransformer()
        cpt.addTransformer(MarginPageTransformer(10))  // 设置页面的间距
        cpt.addTransformer(ScaleInTransformer()) // 切换效果

        mBind.vp2.setPageTransformer(cpt)

        // 滑动事件监听根据需要重写,OnPageChangeCallback是个抽象类
        mBind.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

    override fun initObserve(): Nothing? = null

    @BusUtils.Bus(tag = MyConstant.TAG_NO_PARAM)
    fun oneParamFun(triple: Triple<String, Boolean, Any>) {
        LogUtils.d(triple.first)
    }

}