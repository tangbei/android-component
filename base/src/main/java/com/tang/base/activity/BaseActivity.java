package com.tang.base.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding2.view.RxView;
import com.tang.base.router.ActivityRouterManager;
import com.tang.base.screenadapt.strategy.CustomAdapt;
import com.tang.base.viewmodel.BaseViewModel;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2020/3/17.
 */
public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends RxAppCompatActivity implements CustomAdapt,Observer {

    protected V dataBinding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initData(savedInstanceState);
    }

    /**
     * 初始化DataBinding绑定服务
     */
    private void initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        //获取viewModel
        this.viewModel = getViewModel();
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
        }
        if (null != dataBinding){
            dataBinding.executePendingBindings();
            //让viewModel拥有view的生命周期
            getLifecycle().addObserver(viewModel);
            viewModel.injectLifecycleProvider(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected VM getViewModel(){
        return null;
    }

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

    @Override
    public boolean isBaseWidth() {
        return true;
    }

    @Override
    public void onChanged(Object o) {

    }

    /**
     * 创建viewModel
     * @param activity
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends ViewModel> T createViewModel(Activity activity, Class<T> modelClass){
        return ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication()).create(modelClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(viewModel);
//        if (null != viewModel) {
//            viewModel.onCleared();
//        }
        if (null != dataBinding){
            dataBinding.unbind();
        }
//        ActivityRouterManager.getInstance().release();
    }
}
