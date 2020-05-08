package com.base.app.module.common.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.base.library.base.BActivity;
import com.base.library.mvvm.core.VMViewModel;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

abstract public class Text<VM extends VMViewModel> extends BActivity {

    private VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    public void createViewModel() {
//        if (mViewModel == null) {
//            Class modelClass;
//            Type type = getClass().getGenericSuperclass();
//            if (type instanceof ParameterizedType) {
//                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
//            } else {
//                //如果没有指定泛型参数，则默认使用BaseViewModel
//                modelClass = VMViewModel.class;
//            }
//            mViewModel = (VM) ViewModelProvider(this).get(modelClass.getClasses());
//        }
//    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = VMViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
        }
    }

    //    public void createViewModel() {
//        if (mViewModel == null) {
//            Class modelClass;
//            Type type = getClass().getGenericSuperclass();
//            if (type instanceof ParameterizedType) {
//                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
//            } else {
//                //如果没有指定泛型参数，则默认使用BaseViewModel
//                modelClass = BaseViewModel.class;
//            }
//            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
//        }
//    }

}

