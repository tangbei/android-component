/**
 * Copyright (C), 2019-2020
 * FileName: MvvmFragment
 * Author: tangbei
 * Date: 2020/4/15 2:07 PM
 * Description:
 */
package com.tang.base.fragment;

import android.app.ActivityManager;
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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tang.base.viewmodel.BaseViewModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: java类作用描述 
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements Observer {

    protected V dataBinding;
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
        dataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        if (null == viewModel){
            Class modelClass;
            Type type = getClass().getGenericSuperclass();//返回该class的所有类(类、接口等)
            //如果type为泛型实例，如：Collection<T>等
            if (type instanceof ParameterizedType){
                //获取泛型实例的第二个，第一个为dataBinding,第二个为viewModel
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            }else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this,modelClass);
        }
        if (getBindingVariable() > 0){
            //关联viewModel
            dataBinding.setVariable(getBindingVariable(),viewModel);
            dataBinding.executePendingBindings();
        }
        //绑定viewModel生命周期
        getLifecycle().addObserver(viewModel);
        //注入rxLifecycle生命周期
//        viewModel.injectLifecycleProvider(this);

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
    protected VM getViewModel(){
        return null;
    }

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


    private void registerContact(){
//        registerForActivityResult();
    }

    @Override
    public void onChanged(Object o) {

    }

    public void startActivity(){
    }

    protected <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> modelClass){
        return ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication()).create(modelClass);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(viewModel);
//        if (null != viewModel){
//            viewModel.onCleared();
//
//        }
        if (null != dataBinding){
            dataBinding.unbind();
        }
    }
}
