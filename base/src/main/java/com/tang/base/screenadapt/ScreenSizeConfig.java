package com.tang.base.screenadapt;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.tang.base.screenadapt.lifecycle.ActivityLifecycleCallBacksImpl;
import com.tang.base.screenadapt.strategy.DefaultScreenAdaptStrategy;
import com.tang.base.screenadapt.strategy.ScreenAdaptStrategy;
import com.tang.base.utils.LogUtil;
import com.tang.base.utils.ScreenUtil;

import java.lang.reflect.Field;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: java类作用描述
 */
public class ScreenSizeConfig {

    private static final String TAG = "screenSizeConfig-->";
    private static volatile ScreenSizeConfig instance;
    private Application mApplication;
    /**
     * 设计图上的总宽度，单位dp
     */
    private int mDesignWidthDp;
    /**
     * 设计图上的总高度，单位dp
     */
    private int mDesignHeightDp;
    /**
     * 当前设备的屏幕总宽度，单位px
     */
    private int mScreenWidth;
    /**
     * 当前设备的屏幕总高度，单位px
     */
    private int mScreenHeight;
    /**
     * 状态栏高度
     * 当 {@link #isUseDeviceSize} 为 {@code false}时，会将 {@link #mScreenHeight} 减去状态栏高度
     * 默认是实际全屏幕高度
     */
    private int mStatusBarHeight;
    /**
     * 获取初始逻辑密度{@link DisplayMetrics#density}
     */
    private float mInitialDensity = -1;
    /**
     * 初始屏幕密度{@link DisplayMetrics#densityDpi}
     */
    private int mInitialDensityDpi;
    /**
     * 初始字体密度{@link DisplayMetrics#scaledDensity}
     */
    private float mInitialScaleDensity;
    /**
     * 初始屏幕宽度{@link Configuration#screenWidthDp}
     */
    private int mInitialScreenWidthDp;
    /**
     * 初始屏幕高度{@link Configuration#screenHeightDp}
     */
    private int mInitialScreenHeightDp;
    /**
     * 表示是否使用设备的实际尺寸做适配
     * {@code true} 表示屏幕高度 包含状态栏的高度
     * {@code false} 表示屏幕高度为减去状态了的高度
     * 默认为true
     */
    private boolean isUseDeviceSize = true;
    /**
     * 为了保证在不同宽高比屏幕上显示效果完全一致，
     * 所以本方案适配时，是以设计图宽度的比例或者设计图高度的比例来应用到每个view上
     * 就是只能以一个为基准，从而使每个view的宽和高用同样的比例缩放。
     * {@code true} 表示以宽为基准等比例缩放
     * {@code false} 表示以高为基准等比例缩放
     */
    private boolean isBaseOnWidth = true;
    /**
     * 是否屏蔽系统字体大小对 该适配方案的影响
     * {@code true} app内的字体大小将不会跟随系统设置字体大小而改变
     * {@code false} 则会跟随系统设置中的字体大小而改变
     * 默认为 {@code true}
     */
    private boolean isExcludeFontScale;
    /**
     * 屏幕方向，{@code true} 为竖屏，{@code false} 为横屏
     */
    private boolean isVertical;
    /**
     * Miui系统或存在mTmpMetrics字段的系统
     */
    private Field mTmpMetricsField;
    /**
     * 使用activity的生命周期实现类处理。
     * 方便管理，侵入性低
     */
    private ActivityLifecycleCallBacksImpl mActivityLifecycleCallBacks;

    private ScreenSizeConfig(){

    }

    public static ScreenSizeConfig getInstance(){
        if (null == instance){
            synchronized (ScreenSizeConfig.class){
                if (null == instance){
                    instance = new ScreenSizeConfig();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return mApplication;
    }

    /**
     * 返回 {@link #isBaseOnWidth}
     * @return
     */
    public boolean isBaseOnWidth(){
        return isBaseOnWidth;
    }

    /**
     * 设置全局按照宽度/高度 进行适配
     * @param baseOnWidth
     * @return
     */
    public ScreenSizeConfig setBaseOnWidth(boolean baseOnWidth){
        this.isBaseOnWidth = baseOnWidth;
        return this;
    }

    /**
     * 获取 {@link #mDesignWidthDp}
     * @return
     */
    public int getDesignWidthDp() {
        return mDesignWidthDp;
    }

    /**
     * 获取 {@link #mDesignHeightDp}
     * @return
     */
    public int getDesignHeightDp() {
        return mDesignHeightDp;
    }

    public ScreenSizeConfig init(Application application){
        return init(application, true,null);
    }

    public ScreenSizeConfig init(final Application application, boolean isBaseOnWidth, ScreenAdaptStrategy adaptStrategy){
        this.mApplication = application;
        this.isBaseOnWidth = isBaseOnWidth;

        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        final Configuration configuration = Resources.getSystem().getConfiguration();
        //设置两个默认值
        mDesignWidthDp = 360;
        mDesignHeightDp = 360;
        //这里可以写拿到的宽或者高

        //判断是否为竖屏
        isVertical = application.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        int[] screenSize = ScreenUtil.getScreenSize(application);
        mScreenWidth = screenSize[0];
        mScreenHeight = screenSize[1];
        mStatusBarHeight = ScreenUtil.getStatusBarHeight();
        LogUtil.d(TAG,"mDesignWidth = " + mDesignWidthDp + ", mDesignHeight = " + mDesignHeightDp + ", mScreenWidth = " + mScreenWidth + ", mScreenHeight = " + mScreenHeight + ", mStatusBarHeight = " + mStatusBarHeight);

        mInitialScaleDensity = displayMetrics.scaledDensity;
        mInitialDensity = displayMetrics.density;
        mInitialDensityDpi = displayMetrics.densityDpi;
        mInitialScreenWidthDp = configuration.screenWidthDp;
        mInitialScreenHeightDp = configuration.screenHeightDp;

        //系统配置更改，后的注册监听（例如字体大小配置更改等）
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {
                if (null != newConfig){
                    if (newConfig.fontScale > 0){
                        //获取配置更改后的字体密度
                        mInitialScaleDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
                        LogUtil.d(TAG,"mInitialScaleDensity = " + mInitialScaleDensity + " on ConfigurationChanged");
                    }
                    isVertical = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
                    int[] screenSize = ScreenUtil.getScreenSize(application);
                    mScreenWidth = screenSize[0];
                    mScreenHeight = screenSize[1];
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
        LogUtil.d(TAG,"");

        mActivityLifecycleCallBacks = new ActivityLifecycleCallBacksImpl(adaptStrategy == null ? new DefaultScreenAdaptStrategy() : adaptStrategy);
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallBacks);

        //判断是否是miui系统或存在mTmpMetrics的系统
        if ("MiuiResources".equals(application.getResources().getClass().getSimpleName()) || "XResources".equals(application.getResources().getClass().getSimpleName())) {
            try {
                mTmpMetricsField = Resources.class.getDeclaredField("mTmpMetrics");
                mTmpMetricsField.setAccessible(true);
            } catch (Exception e) {
                mTmpMetricsField = null;
            }
        }
        return this;
    }

    /**
     * 获取 {@link #mScreenWidth}
     * @return
     */
    public int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public int getScreenHeight() {
        return isUseDeviceSize ? mScreenHeight : mScreenHeight - mStatusBarHeight;
    }

    /**
     * 获取 {@link #isUseDeviceSize}
     * @return
     */
    public boolean isUseDeviceSize() {
        return isUseDeviceSize;
    }

    /**
     * 获取 {@link #isExcludeFontScale}
     * @return
     */
    public boolean isExcludeFontScale() {
        return isExcludeFontScale;
    }

    /**
     * 设置 {@link #isExcludeFontScale}
     * @param excludeFontScale
     * @return
     */
    public ScreenSizeConfig setExcludeFontScale(boolean excludeFontScale) {
        isExcludeFontScale = excludeFontScale;
        return this;
    }

    /**
     * 获取 {@link #mInitialScaleDensity}
     * @return
     */
    public float getInitialScaleDensity() {
        return mInitialScaleDensity;
    }

    /**
     * 获取 {@link #mInitialDensity}
     * @return
     */
    public float getInitialDensity() {
        return mInitialDensity;
    }

    /**
     * 获取 {@link #mInitialDensityDpi}
     * @return
     */
    public int getInitialDensityDpi() {
        return mInitialDensityDpi;
    }

    /**
     * 获取 {@link #mInitialScreenWidthDp}
     * @return
     */
    public int getInitialScreenWidthDp() {
        return mScreenWidth;
    }

    /**
     * 获取 {@link #mInitialScreenHeightDp}
     * @return
     */
    public int getInitialScreenHeightDp() {
        return mInitialScreenHeightDp;
    }

    /**
     * 获取 {@link #mTmpMetricsField}
     * @return
     */
    public Field getTmpMetricsField() {
        return mTmpMetricsField;
    }
}
