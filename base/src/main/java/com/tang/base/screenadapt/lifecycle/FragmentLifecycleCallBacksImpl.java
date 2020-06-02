package com.tang.base.screenadapt.lifecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tang.base.screenadapt.strategy.ScreenAdaptStrategy;
import com.tang.base.screenadapt.strategy.DefaultScreenAdaptStrategy;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: {@link Fragment}生命周期监听回调，目的是统一调用执行{@link DefaultScreenAdaptStrategy}
 */
public class FragmentLifecycleCallBacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    private ScreenAdaptStrategy mScreenAdaptStrategy;

    public FragmentLifecycleCallBacksImpl(ScreenAdaptStrategy adaptStrategy) {
        this.mScreenAdaptStrategy = adaptStrategy;
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle savedInstanceState) {
        if (null != mScreenAdaptStrategy){
            mScreenAdaptStrategy.applyAdapt(fragment,fragment.getActivity());
        }
    }

    /**
     * 设置屏幕适配的策略类
     * @param screenAdaptStrategy
     */
    public void setScreenAdaptStrategy(ScreenAdaptStrategy screenAdaptStrategy){
        this.mScreenAdaptStrategy = screenAdaptStrategy;
    }


}
