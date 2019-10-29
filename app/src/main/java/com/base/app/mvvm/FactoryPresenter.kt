package com.base.app.mvvm

import com.base.library.mvp.BPresenter

object FactoryPresenter {

    fun <T : BPresenter> createPresenter(cla: Class<T>): T {
        return cla.newInstance()
    }

}