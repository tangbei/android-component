package com.tang.base.screenadapt.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.tang.base.router.ActivityRouterManager;
import com.tang.base.screenadapt.strategy.ScreenAdaptStrategy;
import com.tang.base.screenadapt.strategy.DefaultScreenAdaptStrategy;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: {@link Activity}生命周期监听，目的是在{@code onCreate}阶段就执行{@link DefaultScreenAdaptStrategy}
 */
public class ActivityLifecycleCallBacksImpl implements Application.ActivityLifecycleCallbacks {

    private ScreenAdaptStrategy mAdaptStrategy;
    private FragmentLifecycleCallBacksImpl mFragmentLifecycleCallBacks;

    public ActivityLifecycleCallBacksImpl(ScreenAdaptStrategy adaptStrategy) {
        this.mFragmentLifecycleCallBacks = new FragmentLifecycleCallBacksImpl(adaptStrategy);
        this.mAdaptStrategy = adaptStrategy;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        if (null != mFragmentLifecycleCallBacks && activity instanceof FragmentActivity){
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycleCallBacks,true);
        }

        if (null != mAdaptStrategy){
            mAdaptStrategy.applyAdapt(activity,activity);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        ActivityRouterManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        ActivityRouterManager.getInstance().removeActivity(activity);
    }

    /**
     * 设置屏幕适配的策略类
     * @param adaptStrategy
     */
    public void setAdaptStrategy(ScreenAdaptStrategy adaptStrategy) {
        this.mAdaptStrategy = adaptStrategy;
        if (null != mFragmentLifecycleCallBacks){
            mFragmentLifecycleCallBacks.setScreenAdaptStrategy(adaptStrategy);
        }
    }
}
