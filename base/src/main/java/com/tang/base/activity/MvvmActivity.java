package com.tang.base.activity;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.tang.base.factory.BaseViewModelFactory;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2020/3/17.
 */
public abstract class MvvmActivity<V extends ViewDataBinding,VM extends ViewModel & LifecycleObserver> extends AppCompatActivity implements Observer {

    protected V viewDataBinding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        if (null != viewModel){
            //如果不为空，绑定生命周期
            getLifecycle().addObserver(viewModel);
        }
        initData(savedInstanceState);
    }

    /**
     * 初始化DataBinding绑定服务
     */
    private void initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if (getBindingVariable() > 0){
            //关联viewModel
            viewDataBinding.setVariable(getBindingVariable(),viewModel);
        }
        //
        viewDataBinding.executePendingBindings();
    }

    /**
     * 获取Layout
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 获取viewmodel
     * @return
     */
    protected abstract VM getViewModel();

    /**
     * 如果布局中使用了layout的实体绑定
     * 需要获取绑定的layout实体对象，则重写此方法
     * @return
     */
    protected int getBindingVariable(){
        return 0;
    }

    /**
     * 初始化
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    private void initViewModel() {

    }

    @Override
    public void onChanged(Object o) {

    }
}
