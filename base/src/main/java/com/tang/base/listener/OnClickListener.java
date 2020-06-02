package com.tang.base.listener;

import android.view.View;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: 重写onClick方法，防止重复点击操作
 */
public abstract class OnClickListener implements View.OnClickListener {

    /**
     * 最后点击事件
     */
    private long mLastClickTime = 0L;
    /**
     * 重复点击间隔
     */
    private long timeInterval = 3000L;

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval){
            click(v);
            mLastClickTime = nowTime;
        }
    }

    protected abstract void click(View view);
}
