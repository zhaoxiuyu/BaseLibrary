package com.base.app.module.common.ui

import android.content.Intent
import androidx.lifecycle.Observer
import com.base.app.R
import com.base.app.module.common.viewmodel.RegisterViewModel
import com.base.library.base.VMActivity
import com.base.library.entitys.Banner
import com.base.library.entitys.Chapters
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : VMActivity<RegisterViewModel>() {

//    private val vm by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    private val sb = StringBuilder("")

    override fun initArgs(intent: Intent?) {
    }

    override fun initView() {
        initContentView(R.layout.activity_register)
    }

    override fun initData() {
        but1.setOnClickListener {
            vm?.getChapters()?.observe(this, Observer { it1 ->
                // 重写回调(非必需)，根据不同的状态进行处理,下面这个对成功提示框单独做定制修改
                it1.handler(object : OnCallback<List<Chapters>>() {
                    override fun onSuccess(
                        msg: String,
                        data: List<Chapters>?,
                        isFinish: Boolean
                    ) {
                        showDialog()
                    }
                })

                sb.delete(0, sb.length)
                it1.data?.forEach { sb.appendln(it.name) }
                tv.text = sb.toString()
            })
        }
        but2.setOnClickListener {
            vm?.getBanner()?.observe(this, Observer { it1 ->

                it1.handler(object : OnCallback<List<Banner>>() {})

                sb.delete(0, sb.length)
                it1.data?.forEach { sb.appendln(it.title) }
                tv.text = sb.toString()
            })
        }
    }

}