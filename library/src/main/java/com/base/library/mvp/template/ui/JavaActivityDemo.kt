package com.base.library.mvp.template.ui

/**
 * 如果用Java代码写Activity，案例如下：
 */

//public class ActivityTest extends BActivity<VPPresenter> implements _VPView {
//
//    @Override
//    public void initArgs(Intent intent) {
//    }
//
//    @Override
//    public void initView() {
//        initContentView(R.layout.base_activity_test);
//        setMPresenter(new _VPPresenter(this));
//        getMPresenter().getData(null);
//    }
//
//    @Override
//    public void initData() {
//        DialogFragment dialogFragment = new DialogFragment();
//        dialogFragment.dismiss();
//    }
//
//    @Override
//    public void bindData(BaseResponse baseResponse) {
//    }
//
//    @Override
//    public void bindError(@NotNull String string) {
//    }
//
//}
//
////套一层BaseActivity
//public class BaseActivity2<T extends VPPresenter> extends BActivity {
//
//    @Override
//    public void initArgs(@NotNull Intent intent) {
//    }
//
//    @Override
//    public void initView() {
//    }
//
//    @Override
//    public void initData() {
//    }
//
//}
