/**
 * Copyright (C), 2019-2020
 * FileName: MvvmBaseViewModel
 * Author: tangbei
 * Date: 2020/4/15 1:14 PM
 * Description:
 */
package com.tang.base.viewmodel;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.tang.base.model.ILoadModelListener;
import com.tang.base.model.MvvmBaseModel;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: java类作用描述 
 */
public abstract class MvvmBaseViewModel<M extends MvvmBaseModel,T> extends ViewModel implements LifecycleObserver, ILoadModelListener<T> {

    protected M model;

    protected abstract MvvmBaseViewModel init();

    /**
     * 请求网络
     */
    public void toRefresh(){
        if (null != model){
            model.refresh();
        }
    }

    /**
     * ViewModel销毁时，解除订阅
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (null != model){
            model.cancel();
        }
    }

    @Override
    public void onLoadFinish(MvvmBaseModel model, T data) {
        if (this.model == model){

        }
    }

    @Override
    public void onLoadFail(MvvmBaseModel model, String prompt) {
        if (this.model == model){

        }
    }
}
