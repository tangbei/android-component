/**
 * Copyright (C), 2019-2020
 * FileName: MvvmBaseViewModel
 * Author: tangbei
 * Date: 2020/4/15 1:14 PM
 * Description:
 */
package com.tang.base.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import com.tang.base.livedata.LiveDataBus;
import com.tang.base.livedata.SingleLiveData;
import com.tang.base.model.BaseModel;
import com.tang.base.model.ILoadModelListener;
import com.tang.base.router.ActivityRouterManager;
import com.tang.base.utils.LogUtil;
import com.tang.component.network.cache.Cache2;
import com.tang.component.network.cache.CacheType2;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: java类作用描述 
 */
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IBaseViewModel, ILoadModelListener {

    protected M model;
    private LiveDataBus mLiveDataBus;
    private WeakReference<LifecycleProvider> mLifecycle;
    public SingleLiveData<Integer> mLiveData;
    public ObservableField<String> strObservable;
    private Cache2.Factory mFactory;

    public BaseViewModel(@NonNull Application application) {
        this(application,null);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
        mLiveData = new SingleLiveData<>();
        strObservable = new ObservableField<>();
    }

    @Override
    public void onCreate() {
        if (null != model){
            model.register(this);
        }
    }

    @Override
    public void onStart() {
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onStart");
    }

    @Override
    public void onResume() {
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onResume");
    }


    /**
     * 设置绑定的生命周期托管对象
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle){
        this.mLifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider(){
        return mLifecycle.get();
    }

    /**
     * 请求网络
     */
    public void refresh(){
        if (null != model){
            model.refresh();
        }
    }


    @Override
    public void onLoadFinish(BaseModel model, Object data) {

    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if (this.model == model){

        }
    }

    @Override
    public void startActivity(@NonNull String path, Bundle bundle) {
        ActivityRouterManager.getInstance().startActivity(path,bundle);
    }

    @Override
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this.getApplication().getApplicationContext(),clazz);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        ActivityRouterManager.getInstance().startActivity(intent);
    }

    @Override
    public void onPause() {
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onPause");
    }

    @Override
    public void onStop() {
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onStop");
    }

    @Override
    public void onDestroy() {
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onDestroy");
    }

    /**
     * ViewModel销毁时，解除订阅
     */
    @Override
    public void onCleared() {
        super.onCleared();
        LogUtil.d("BaseViewModel-->"+this+"-->"+"onCleared");
        if (null != mLifecycle){
            mLifecycle.clear();
        }
        if (null != mLiveData){
            mLiveData = null;
        }
        if (null != strObservable){
            strObservable = null;
        }
        if (null != model){
            model.unRegister(this);
            model.onCleared();
        }
    }

}
