package com.base.app.module.newfunction.ui

import android.content.Intent
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.base.app.R
import com.base.app.module.newfunction.adapter.ButtonAdapter
import com.base.app.module.newfunction.adapter.ImageAdapter
import com.base.app.module.newfunction.adapter.TextAdapter
import com.base.app.module.newfunction.viewmodel.FunctionViewModel
import com.base.app.utils.TestDatas
import com.base.library.mvvm.core.VMActivity
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : VMActivity<FunctionViewModel>() {

    private val bAdapter by lazy { ButtonAdapter() }
    private val iAdapter by lazy { ImageAdapter() }
    private val tAdapter by lazy { TextAdapter() }

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_function)
    }

    override fun initData() {

        val mAdapter = MergeAdapter(bAdapter, iAdapter, tAdapter)

        rv.adapter = mAdapter
        rv.layoutManager = LinearLayoutManager(this)

        bAdapter.setNewInstance(TestDatas.getStrs())
        iAdapter.setNewInstance(TestDatas.getStrs())
        tAdapter.setNewInstance(TestDatas.getStrs())

        bAdapter.addChildClickViewIds(R.id.but)
        bAdapter.setOnItemChildClickListener { p, view, i ->
            LogUtils.d("bAdapter")
        }

        iAdapter.addChildClickViewIds(R.id.iv)
        iAdapter.setOnItemChildClickListener { p, view, i ->
            LogUtils.d("bAdapter")
        }

        tAdapter.addChildClickViewIds(R.id.tv)
        tAdapter.setOnItemChildClickListener { p, view, i ->
            LogUtils.d("bAdapter ${(view as TextView).text}")
        }

    }

}