package com.tang.common.aroute;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.common.BuildConfig;
import com.tang.common.R;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/19
 * Description: java类作用描述
 */
public class RouterManager {

    private static RouterManager instance = null;
    private Context mContext;

    private RouterManager() {
//        this.mContext = context;
    }

    public static RouterManager getInstance(){
        if (null == instance){
            synchronized (RouterManager.class){
                if (null == instance){
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }

    public Fragment getFragment(String path){
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    public void startActivity(@NonNull String path){
        ARouter.getInstance()
                .build(path)
                .withTransition(R.anim.push_right_in, R.anim.push_left_out)
                .navigation();
    }
}
