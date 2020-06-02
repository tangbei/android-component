package com.tang.base.screenadapt;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.tang.component.network.Preconditions;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: 屏幕适配的核心实现类
 * 使用今日头条适配方案
 */
public class ScreenAdaptSize {
    private static final int MODE_SHIFT = 30;
    private static final int MODE_MASK  = 0x3 << MODE_SHIFT;
    private static final int MODE_ON_WIDTH  = 1 << MODE_SHIFT;
    private static final int MODE_DEVICE_SIZE  = 2 << MODE_SHIFT;
    /**
     * 缓存数据
     */
    private static SparseArray<DisplayMetricsInfo> mCache = new SparseArray<>();

    /**
     * 屏幕适配转换
     * 使用 {@link ScreenSizeConfig} 进行初始化默认配置
     * @param activity
     */
    public static void screenConvertDensityGlobal(Activity activity){
        if (ScreenSizeConfig.getInstance().isBaseOnWidth()){
            //以宽适配
            screenConvertDensityOnWidth(activity,ScreenSizeConfig.getInstance().getDesignWidthDp());
        }else {
            //以高适配
            screenConvertDensityOnHeight(activity,ScreenSizeConfig.getInstance().getDesignHeightDp());
        }
    }

    /**
     * 以宽度为基准适配
     * @param activity
     * @param designWidth 设计图的总宽度
     */
    public static void screenConvertDensityOnWidth(Activity activity, float designWidth){
        screenConvertDensity(activity,designWidth,true);
    }

    /**
     * 以高度为基准适配
     * @param activity
     * @param designHeight 设计图的总高度
     */
    public static void screenConvertDensityOnHeight(Activity activity,float designHeight){
        screenConvertDensity(activity,designHeight,false);
    }

    /**
     * 今日头条适配的核心代码
     * @param activity 上下文activity
     * @param isBaseOnWidth true是以宽适配，false是以高适配
     * @param sizeDp 如果{@code isBaseOnWidth = true},则{@code sizeDp}值为宽，否则为高
     */
    public static void screenConvertDensity(@NonNull final Activity activity, float sizeDp, boolean isBaseOnWidth){


        float designSize = sizeDp;
        int screenSize = isBaseOnWidth ? ScreenSizeConfig.getInstance().getScreenWidth() : ScreenSizeConfig.getInstance().getScreenHeight();

        int key = Math.round(designSize + screenSize) & ~MODE_MASK;
        key = isBaseOnWidth ? (key | MODE_ON_WIDTH) : (key & ~MODE_ON_WIDTH);
        key = ScreenSizeConfig.getInstance().isUseDeviceSize() ? (key | MODE_DEVICE_SIZE) : (key & ~MODE_DEVICE_SIZE);

        DisplayMetricsInfo displayMetricsInfo = mCache.get(key);

        float targetDensity = 0;
        int targetDensityDpi = 0;
        float targetScaledDensity = 0;
        int targetScreenWidth = 0;
        int targetScreenHeight = 0;

        if (displayMetricsInfo == null){
            if (isBaseOnWidth){
                targetDensity = ScreenSizeConfig.getInstance().getScreenWidth() * 1.0f / sizeDp;
            }else {
                targetDensity = ScreenSizeConfig.getInstance().getScreenHeight() * 1.0f / sizeDp;
            }
            //系统字体缩放因子 = 是否屏蔽跟随系统字体改变 ？ 1 ：初始字体密度/初始逻辑密度
            float systemFontScale = ScreenSizeConfig.getInstance().isExcludeFontScale() ? 1 : ScreenSizeConfig.getInstance().getInitialScaleDensity() * 1.0f / ScreenSizeConfig.getInstance().getInitialDensity();
            //获取当前字体密度 = 字体缩放因子 * 宽/高缩放比
            targetScaledDensity = targetDensity * systemFontScale;

            //当前屏幕密度dpi = 缩放比 * 160
            targetDensityDpi = (int) (targetDensity * 160);
            //当前屏幕宽度 = 当前屏幕宽度 * 缩放比
            targetScreenWidth = (int) (ScreenSizeConfig.getInstance().getScreenWidth() * 1.0f / targetDensity);
            //当前屏幕宽度 = 当前屏幕高度 * 缩放比
            targetScreenHeight = (int) (ScreenSizeConfig.getInstance().getScreenHeight() * 1.0f / targetDensity);

            //添加到缓存
            mCache.put(key, new DisplayMetricsInfo(targetDensity,targetDensityDpi,targetScaledDensity,targetScreenWidth,targetScreenHeight));
        }else {
            targetDensity = displayMetricsInfo.getDensity();
            targetDensityDpi = displayMetricsInfo.getDensityDpi();
            targetScaledDensity = displayMetricsInfo.getScaledDensity();
            targetScreenWidth = displayMetricsInfo.getScreenWidth();
            targetScreenHeight = displayMetricsInfo.getScreenHeight();
        }

        setDensity(activity,targetDensity,targetDensityDpi,targetScaledDensity);
        setScreenSizeDp(activity,targetScreenWidth,targetScreenHeight);
    }

    /**
     * 取消适配
     * @param activity
     */
    public static void cancelAdapt(Activity activity) {
        Preconditions.checkMainThread();
        setDensity(activity,ScreenSizeConfig.getInstance().getInitialDensity(),ScreenSizeConfig.getInstance().getInitialDensityDpi(),ScreenSizeConfig.getInstance().getInitialDensityDpi());
        setScreenSizeDp(activity,ScreenSizeConfig.getInstance().getInitialScreenWidthDp(),ScreenSizeConfig.getInstance().getInitialScreenHeightDp());
    }

    /**
     * 重新给{@link Configuration}赋值
     * @param activity
     * @param targetScreenWidth
     * @param targetScreenHeight
     */
    private static void setScreenSizeDp(Activity activity, int targetScreenWidth, int targetScreenHeight) {
        //给activity的Configuration赋值
        Configuration activityConfiguration = activity.getResources().getConfiguration();
        setScreenSizeDp(activityConfiguration,targetScreenWidth,targetScreenHeight);

        //给application的Configuration赋值
        Configuration appConfiguration = ScreenSizeConfig.getInstance().getApplication().getResources().getConfiguration();
        setScreenSizeDp(appConfiguration,targetScreenWidth,targetScreenHeight);
    }

    /**
     * 具体给{@link Configuration}赋值
     * @param configuration  {@link Configuration}
     * @param screenWidthDp  {@link Configuration#screenWidthDp}
     * @param screenHeightDp {@link Configuration#screenHeightDp}
     */
    private static void setScreenSizeDp(Configuration configuration, int screenWidthDp, int screenHeightDp) {
        configuration.screenWidthDp = screenWidthDp;
        configuration.screenHeightDp = screenHeightDp;
    }

    /**
     * 重新给{@link DisplayMetrics}赋值
     * @param activity
     * @param targetDensity
     * @param targetDensityDpi
     * @param targetScaledDensity
     */
    private static void setDensity(Activity activity, float targetDensity, int targetDensityDpi, float targetScaledDensity) {
        //给activity的DisplayMetrics重新赋值
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        setDensity(activityDisplayMetrics,targetDensity,targetDensityDpi,targetScaledDensity);

        //给application的DisplayMetrics重新赋值
        DisplayMetrics appDisplayMetrics = ScreenSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics();
        setDensity(appDisplayMetrics,targetDensity,targetDensityDpi,targetScaledDensity);

        //兼容MIUI
        DisplayMetrics activityDisplayMetricsOnMIUI = getMetricsOnMiui(activity.getResources());
        DisplayMetrics appDisplayMetricsONMIUI = getMetricsOnMiui(ScreenSizeConfig.getInstance().getApplication().getResources());

        if (null != activityDisplayMetricsOnMIUI){
            setDensity(activityDisplayMetricsOnMIUI,targetDensity,targetDensityDpi,targetScaledDensity);
        }
        if (null != appDisplayMetricsONMIUI){
            setDensity(appDisplayMetricsONMIUI,targetDensity,targetDensityDpi,targetScaledDensity);
        }
    }

    /**
     * 具体给{@link DisplayMetrics}赋值操作
     * @param displayMetrics
     * @param targetDensity
     * @param targetDensityDpi
     * @param targetScaledDensity
     */
    private static void setDensity(DisplayMetrics displayMetrics, float targetDensity, int targetDensityDpi, float targetScaledDensity) {
        //给activity的DisplayMetrics重新赋值
        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        displayMetrics.scaledDensity = targetScaledDensity;
    }

    /**
     * 解决 MIUI 更改框架导致的 MIUI7 + Android5.1.1 上出现的失效问题 (以及极少数基于这部分 MIUI 去掉 ART 然后置入 XPosed 的手机)
     * 来源于: https://github.com/Firedamp/Rudeness/blob/master/rudeness-sdk/src/main/java/com/bulong/rudeness/RudenessScreenHelper.java#L61:5
     *
     * @param resources {@link Resources}
     * @return {@link DisplayMetrics}, 可能为 {@code null}
     */
    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if (ScreenSizeConfig.getInstance().getTmpMetricsField() != null) {
            try {
                return (DisplayMetrics) ScreenSizeConfig.getInstance().getTmpMetricsField().get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
