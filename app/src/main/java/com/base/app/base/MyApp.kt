package com.base.app.base

import com.base.library.base.BApplication

class MyApp : BApplication() {

    override fun onCreate() {
        super.onCreate()
        initMethod()
//        DoKit.Builder(this)
//            .productId("0f0894d53fe597a618cb4e0c31e2f536")
//            .build()
    }

}