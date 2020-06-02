package com.tang.common.utils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class CommonUtil {

    /**
     * 获得资源
     * @param context
     * @return
     */
    public static Resources getResources(Context context){
        return context.getResources();
    }

    /**
     * dp 转 px
     * @param context
     * @param dpValue
     * @return pxValue
     */
    public static int dip2px(@NonNull Context context, float dpValue){
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(@NonNull Context context, float pxValue){
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    /**
     * px 转 sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }


}
