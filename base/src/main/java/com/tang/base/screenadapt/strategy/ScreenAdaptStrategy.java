package com.tang.base.screenadapt.strategy;

import android.app.Activity;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: 屏幕适配的策略类
 */
public interface ScreenAdaptStrategy {

    /**
     * 执行屏幕适配逻辑
     * @param target 需要屏幕适配的对象，可能是{@link Activity} 或者 {@link androidx.fragment.app.Fragment}
     * @param activity 需要拿到当前的
     */
    void applyAdapt(Object target, Activity activity);
}
