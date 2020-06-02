package com.tang.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class VectorViewUtil {

    public static void setViewBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * 设置TextView使用Vector做drawableLeft
     *
     * @param drawableWidth   单位是dip
     * @param drawableHeight  单位是dip
     * @param drawablePadding 单位是dip
     */
    public static void setVectorDrawableLeft(Context context,TextView textView, VectorDrawableCompat drawable, int drawableWidth,
                                             int drawableHeight, int drawablePadding) {
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, CommonUtil.dip2px(context,drawableWidth), CommonUtil.dip2px(context,drawableHeight));
        textView.setCompoundDrawablePadding(CommonUtil.dip2px(context,drawablePadding));
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置TextView使用Vector做drawableRight
     *
     * @param drawableWidth   单位是dip
     * @param drawableHeight  单位是dip
     * @param drawablePadding 单位是dip
     */
    public static void setVectorDrawableRight(Context context,TextView textView, VectorDrawableCompat drawable, int drawableWidth,
                                              int drawableHeight, int drawablePadding) {
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, CommonUtil.dip2px(context,drawableWidth), CommonUtil.dip2px(context,drawableHeight));
        textView.setCompoundDrawablePadding(CommonUtil.dip2px(context,drawablePadding));
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置TextView使用Vector做drawableTop
     *
     * @param drawableWidth   单位是dip
     * @param drawableHeight  单位是dip
     * @param drawablePadding 单位是dip
     */
    public static void setVectorDrawableTop(Context context, TextView textView, VectorDrawableCompat drawable, int drawableWidth,
                                            int drawableHeight, int drawablePadding) {
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, CommonUtil.dip2px(context,drawableWidth), CommonUtil.dip2px(context,drawableHeight));
        textView.setCompoundDrawablePadding(CommonUtil.dip2px(context,drawablePadding));
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 设置TextView使用Vector做drawableBottom
     *
     * @param drawableWidth   单位是dip
     * @param drawableHeight  单位是dip
     * @param drawablePadding 单位是dip
     */
    public static void setVectorDrawableBottom(Context context,TextView textView, VectorDrawableCompat drawable, int drawableWidth,
                                               int drawableHeight, int drawablePadding) {
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, CommonUtil.dip2px(context,drawableWidth), CommonUtil.dip2px(context,drawableHeight));
        textView.setCompoundDrawablePadding(CommonUtil.dip2px(context,drawablePadding));
        textView.setCompoundDrawables(null, null, null, drawable);
    }

    /** 设置使用Vector做selector，作为view的背景 */
    public static void setVectorSelectorBackground(View view, VectorDrawableCompat normalVector,
                                                   VectorDrawableCompat pressedVector) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedVector);
        stateListDrawable.addState(new int[]{}, normalVector);
        setViewBackground(view, stateListDrawable);
    }
}
