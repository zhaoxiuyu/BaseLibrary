package com.base.app.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.base.library.interfaces.MyLifecycle
import com.base.library.mvp.BView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

open class BaseViewModel : ViewModel(), MyLifecycle {

    private var bView: WeakReference<BView>? = null
    private var owner: LifecycleOwner? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    fun addCompositeDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(disposable)
    }

    fun setBView(view: BView) {
        bView = WeakReference(view)
    }

    fun getBView(): BView? {
        return bView?.get()
    }

    override fun onCreate(owner: LifecycleOwner) {
        this.owner = owner
    }

    override fun onCleared() {
        super.onCleared()
        bView = null
        mCompositeDisposable?.dispose()
    }

}