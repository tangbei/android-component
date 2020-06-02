package com.tang.common.aroute;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.utils.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/27
 * Description: java类作用描述
 */
public class ActivityRouterManager {

    private ActivityRouterManager instance;
    /**
     * 管理所有存活的activity
     */
    private List<Activity> mActivityList;

    private ActivityRouterManager(){

    }

    public ActivityRouterManager getInstance(){
        if (null == instance){
            synchronized (ActivityRouterManager.class){
                if (null == instance){
                    instance = new ActivityRouterManager();
                }
            }
        }
        return instance;
    }

    public void startActivity(@NonNull String path, Bundle bundle){
        if (null == getTopActivity()){
           ARouter.getInstance().build(path).with(bundle).navigation();
        }
    }

    /**
     *
     * @return
     */
    private Activity getTopActivity() {
        if (null == mActivityList){
            LogUtil.w("mActivityList == null when getTopActivity()");
            return null;
        }
        return mActivityList.size() > 0 ? mActivityList.get(mActivityList.size() - 1) : null;
    }

    /**
     * 返回一个存储未销毁的activity集合
     * @return
     */
    public List<Activity> getActivityList(){
        if (null == mActivityList){
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }

    /**
     * 添加activity到集合
     * @param activity
     */
    public void AddActivity(Activity activity){
        synchronized (ActivityRouterManager.class){
            List<Activity> activities = getActivityList();
            if (!activities.contains(activity)){
                activities.add(activity);
            }
        }
    }

}
