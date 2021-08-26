package com.base.app.module.common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.base.library.mvvm.BViewModel

class MainViewModel : BViewModel() {

    val bnvShowLiveData by lazy { MutableLiveData<Boolean>() }


}