/**
 * Copyright (C), 2019-2020
 * FileName: MvvmFragment
 * Author: tangbei
 * Date: 2020/4/15 2:07 PM
 * Description:
 */
package com.tang.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.tang.base.viewmodel.MvvmBaseViewModel;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: java类作用描述 
 */
public abstract class MvvmFragment<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends Fragment implements Observer {

    protected V viewDataBinding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为true，当屏幕旋转时，当前的fragment会保留，不会随activity一起销毁
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (null != viewModel){
            getLifecycle().addObserver(viewModel);
        }

        if (getBindingVariable() > 0){
            //关联viewModel
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
        }
        viewDataBinding.executePendingBindings();

        initData(savedInstanceState);
    }

    /**
     * 获取fragment的layout
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 获取ViewModel
     * @return
     */
    protected abstract VM getViewModel();

    /**
     * 获取layout的bean
     * @return
     */
    protected abstract int getBindingVariable();

    /**
     * 初始化操作
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);


    @Override
    public void onChanged(Object o) {

    }
}
