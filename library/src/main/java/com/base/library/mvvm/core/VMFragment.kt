package com.base.library.mvvm.core

import com.base.library.base.BFragment

abstract class VMFragment : BFragment(), OnHandleCallback {

    // 旧的使用方式
//abstract class VMFragment<VM : VMViewModel> : BFragment() {
//    protected var vm: VM? = null

//    lateinit var rootView: VB

//    override fun initData(bundle: Bundle?) {
//        dialogState()
//    }

    // 使用反射得到 ViewBinding 的 class
//    private fun instantiation(parent: ViewGroup?) {
//        val type = javaClass.genericSuperclass
//        if (type is ParameterizedType) {
//            val clazz = type.actualTypeArguments[0] as Class<VB>
//            val method = clazz.getMethod(
//                "inflate",
//                LayoutInflater::class.java,
//                ViewGroup::class.java,
//                Boolean::class.java
//            )
//            rootView = method.invoke(null, layoutInflater, parent, false) as VB
//        }
//    }

    /**
     * 默认走这个里面的提示框流程和样式,实现类可以重写这个方法进行定制
     * 如果Fragment和Activity公用同一个ViewModel,如果都注册了这个监听,那么Fragment和Activity都会收到回调
     */
//    open fun dialogState() {
//        BViewModel()?.getState()?.observe(this, Observer { it.handler(this) })
////        vm?.dialogState?.observe(this, Observer { it.handler(object : OnCallback() {}) })
//    }
//
//    override fun onLoading(mRequest: RxRequest) {
//        Log.v("OnHandleCallback", "onLoading")
//        if (mRequest.showLoading) {
//            showLoading(null, mRequest.msg)
//        }
//    }
//
//    override fun onSuccess(mRequest: RxRequest) {
//        Log.v("OnHandleCallback", "onSuccess")
//        // 网络请求是否弹出加载框，就对应的关闭
//        if (mRequest.showLoading) dismissDialog()
//        // 请求成功，是否弹窗提示
//        if (mRequest.showSuccess) {
//            val mListener = getDismissFinish(mRequest.successClickFinish)
//            showDialog(content = mRequest.msg, confirmLi = mListener)
//        }
//    }
//
//    override fun onError(mRequest: RxRequest) {
//        Log.v("OnHandleCallback", "onError")
//        // 网络请求是否弹出加载框，就对应的关闭
//        if (mRequest.showLoading) dismissDialog()
//        // 请求失败，是否弹窗提示
//        if (mRequest.showFail) {
//            val mListener = getDismissFinish(mRequest.failClickFinish)
//            showDialog(content = mRequest.msg, confirmLi = mListener)
//        }
//    }
//
//    override fun onCompleted() {
//        Log.v("OnHandleCallback", "onCompleted")
//    }
//
//    override fun onProgress(progress: Progress?) {
//        Log.v("OnHandleCallback", "onProgress")
//    }

    // 放到Base里面，父类可以统一操作，也可以留给子类重写
//    abstract inner class OnCallback : OnHandleCallback {
//        override fun onLoading(msg: String) {
//            Log.v("OnCallback", "onLoading")
//            showLoading(null, msg)
//        }
//
//        override fun onSuccess(msg: String, isFinish: Boolean) {
//            Log.v("OnCallback", "onSuccess")
//        }
//
//        override fun onError(error: Throwable?, message: String, isFinish: Boolean) {
//            Log.v("OnCallback", "onError")
//            showDialog(content = message)
//        }
//
//        override fun onCompleted() {
//            Log.v("OnCallback", "onCompleted")
//            dismissDialog(false)
//        }
//
//        override fun onProgress(progress: rxhttp.wrapper.entity.Progress?) {
//            Log.v("OnCallback", "onProgress")
//        }
//    }

//    最原始的初始化ViewModel方式
//    private val vm by lazy { ViewModelProvider(this).get(Demo3ViewModel::class.java) }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        createViewModel()
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

//    根据传入的泛型进行初始化ViewModel
//    private fun createViewModel() {
//        vm ?: let {
//            val type = javaClass.genericSuperclass
//            val modelClass = if (type is ParameterizedType) {
//                type.actualTypeArguments[0] as Class<*>
//            } else {
//                VMViewModel::class.java
//            }
//            vm = ViewModelProvider(this).get(modelClass as Class<VMViewModel>) as VM
//        }
//        vm?.stateLiveData?.observe(this, Observer { stateLiveData(it) })
//        mSharedViewModel =
//            ViewModelProvider(activity as AppCompatActivity).get(SharedViewModel::class.java)
//    }

}