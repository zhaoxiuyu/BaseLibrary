package com.base.app.sample.views.activity

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.base.app.R
import com.base.app.base.MyMethod
import com.base.app.databinding.ActivityViewpage2Binding
import com.base.app.sample.views.fragment.ViewPage2Fragment
import com.base.app.sample.views.transformer.ScaleInTransformer
import com.base.library.mvvm.VMActivity

// ViewPage2 Fragment 基本使用
class ViewPage2Activity : VMActivity<ActivityViewpage2Binding>() {

    private val vP2Adapter by lazy { VP2Adapter(MyMethod.getStrs(5)) }

    override fun initArgs(mIntent: Intent?) {}

    override fun initView() {
        setContentView(viewBinding.root)

        viewBinding.butVertical.setOnClickListener {
            viewBinding.vp2.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
        viewBinding.butHorizontal.setOnClickListener {
            viewBinding.vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        // 模拟拖拽,想前一个页面滑动 -300f
        viewBinding.butDrag.setOnClickListener {
            viewBinding.vp2.beginFakeDrag()
            if (viewBinding.vp2.fakeDragBy(-300f)) {
                viewBinding.vp2.endFakeDrag()
            }
        }

//        vp2.isUserInputEnabled = false // 禁止 ViewPage2 滑动
    }

    override fun initData(savedInstanceState: Bundle?) {
//        vp2.adapter = vP2Adapter // 和 RecyclerView 的适配器一样使用
        viewBinding.vp2.adapter = AdapterFragmentPage(this) // 使用 Fragment

        // 实现一屏多页的效果,通过 RecyclerView 设置 Padding 来实现
        viewBinding.vp2.apply {
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

        viewBinding.vp2.setPageTransformer(cpt)

        // 滑动事件监听根据需要重写,OnPageChangeCallback是个抽象类
        viewBinding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

    override fun registerObserve() {
    }

}

class VP2Adapter(private val mStrs: MutableList<String>) :
    RecyclerView.Adapter<VP2Adapter.PageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_viewpage2, parent, false)
        return PageViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return mStrs.size
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bindView(mStrs[position])
    }

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextView: TextView = itemView.findViewById(R.id.itemTv)
        fun bindView(str: String) {
            mTextView.text = str
        }
    }
}

class AdapterFragmentPage(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = SparseArray<Fragment>()

    init {
        fragments.put(0, ViewPage2Fragment.newInstance(0))
        fragments.put(1, ViewPage2Fragment.newInstance(1))
        fragments.put(2, ViewPage2Fragment.newInstance(2))
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
