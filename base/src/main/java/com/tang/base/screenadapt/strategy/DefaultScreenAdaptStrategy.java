package com.tang.base.screenadapt.strategy;

import android.app.Activity;

import com.tang.base.screenadapt.ScreenAdaptSize;
import com.tang.base.utils.LogUtil;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: 屏幕适配策略的实现类
 * {@link com.tang.base.screenadapt.lifecycle.ActivityLifecycleCallBacksImpl} 和 {@link com.tang.base.screenadapt.lifecycle.FragmentLifecycleCallBacksImpl}
 * 都会调用{@code applyAdapt}方法，此为具体实现
 */
public class DefaultScreenAdaptStrategy implements ScreenAdaptStrategy {
    @Override
    public void applyAdapt(Object target, Activity activity) {
        LogUtil.w("我是屏幕适配策略:"+target);


        if (target instanceof CancelAdapt){
            LogUtil.w(target.getClass().getName()+" 已取消适配");
            ScreenAdaptSize.cancelAdapt(activity);
            return;
        }

        ScreenAdaptSize.screenConvertDensityGlobal(activity);

    }
}
