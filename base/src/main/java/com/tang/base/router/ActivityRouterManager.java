package com.tang.base.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.R;
import com.tang.base.utils.LogUtil;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/27
 * Description: java类作用描述
 */
public class ActivityRouterManager {

    private static volatile ActivityRouterManager instance;
    private static final int AnimationType_BOTTOM = 0;
    private static final int AnimationType_LEFT = 1;
    private static final int AnimationType_RIGHT = 2;
    /**
     * 管理所有存活的activity
     */
    private Stack<WeakReference<Activity>> mActivityStack;

    private ActivityRouterManager() {

    }

    public static ActivityRouterManager getInstance() {
        if (null == instance) {
            synchronized (ActivityRouterManager.class) {
                if (null == instance) {
                    instance = new ActivityRouterManager();
                }
            }
        }
        return instance;
    }

    /**
     * 让在栈顶的activity打开指定的activity
     * @param path
     */
    public void startActivity(@NonNull String path){
        startActivity(path,null);
    }

    /**
     * 让在栈顶的activity打开指定的activity
     * @param path
     * @param bundle
     */
    public void startActivity(@NonNull String path, Bundle bundle){
        startActivity(path,bundle,AnimationType_LEFT);
    }

    /**
     * 让在栈顶的activity打开指定的activity
     * @param path
     * @param bundle
     * @param animation
     */
    public void startActivity(@NonNull String path, Bundle bundle, int animation){
        int enterAnim = animation == AnimationType_LEFT ? R.anim.push_right_in : (animation == AnimationType_BOTTOM ? R.anim.push_bottom_in : 0);
        int exitAnim = animation == AnimationType_LEFT ? R.anim.push_left_out : (animation == AnimationType_BOTTOM ? R.anim.push_top_out : 0);
        if (null == getTopActivity()){
            //如果没有前台的activity就使用new_task模式启动activity
            if (null == bundle){
                ARouter.getInstance().build(path).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).withTransition(enterAnim,exitAnim).navigation();
                return;
            }
            ARouter.getInstance().build(path).with(bundle).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK).withTransition(enterAnim,exitAnim).navigation();
            return;
        }
        ARouter.getInstance().build(path).with(bundle).withTransition(enterAnim,exitAnim).navigation();
    }

    /**
     * 获取最近启动的一个{@link Activity}
     * @return
     */
    public Activity getTopActivity() {
        if (null == mActivityStack){
            return null;
        }
        WeakReference<Activity> activityWeakReference = null;
        if (mActivityStack.size() > 0){
            activityWeakReference = mActivityStack.get(mActivityStack.size() - 1);
        }
        return activityWeakReference != null ? activityWeakReference.get() : null;
    }

    /**
     * 添加 {@link Activity} 到 {@link Stack} 集合
     * @param activity 实例
     */
    public void addActivity(Activity activity){
        synchronized (ActivityRouterManager.class){
            Stack<WeakReference<Activity>> activities = getActivityStack();
            if (!activities.contains(new WeakReference<Activity>(activity))){
                activities.add(new WeakReference<Activity>(activity));
            }
        }
    }

    /**
     * 返回一个未销毁的{@link Activity}集合
     * @return
     */
    private Stack<WeakReference<Activity>> getActivityStack() {
        if (null == mActivityStack){
            mActivityStack = new Stack<>();
        }
        return mActivityStack;
    }

    /**
     * 删除{@link Stack}中指定的{@link Activity}
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (null == mActivityStack){
            LogUtil.i("mActivityStack == null when removeActivity(activity)");
            return;
        }
        synchronized (ActivityRouterManager.class){
            if (mActivityStack.contains(new WeakReference<Activity>(activity))){
                mActivityStack.remove(new WeakReference<Activity>(activity));
            }
        }
    }

    /**
     * 删除{@link Stack}中指定的位置{@code location}的{@link Activity}
     * @param location
     * @return
     */
    public boolean removeActivity(int location){
        if (null == mActivityStack){
            LogUtil.i("mActivityStack == null when removeActivity(activity)");
            return true;
        }
        synchronized (ActivityRouterManager.class){
            if (location > 0 && location < mActivityStack.size()){
                WeakReference<Activity> activityWeakReference = mActivityStack.get(location);
                return mActivityStack.remove(activityWeakReference);
            }
        }
        return true;
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    public void checkWeakReference() {
        synchronized (ActivityRouterManager.class) {
            Iterator<WeakReference<Activity>> it = mActivityStack.iterator();
            while(it.hasNext()) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();// 使用迭代器来进行安全的加锁操作
                }
            }
        }
    }

    /**
     * 获取当前{@link Activity}
     * @return 当前（栈顶）{@link Activity}
     */
    public Activity currentActivity() {
        checkWeakReference();
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * 结束除当前{@link Activity}以外的所有{@link Activity}
     *
     * @param activity 不需要结束的{@link Activity}
     */
    public void finishOtherActivity(Activity activity) {
        synchronized (ActivityRouterManager.class){
            Iterator<WeakReference<Activity>> it = mActivityStack.iterator();
            while (it.hasNext()){
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    // 清理掉已经释放的activity
                    it.remove();
                    continue;
                }
                if (temp != activity) {
                    // 使用迭代器来进行安全的加锁操作
                    it.remove();
                    temp.finish();
                }
            }
        }
    }

    /**
     * 结束除指定{@link Activity}以外的所有{@link Activity}
     *
     * @param cls 指定的某类{@link Activity}
     */
    public void finishOtherActivity(Class<?> cls) {
        synchronized (ActivityRouterManager.class){
            Iterator<WeakReference<Activity>> it = getActivityStack().iterator();
            while (it.hasNext()){
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                if (activity == null) {
                    // 清理掉已经释放的activity
                    it.remove();
                    continue;
                }
                if (!activity.getClass().equals(cls)) {
                    // 使用迭代器来进行安全的加锁操作
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束当前{@link Activity}
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的{@link Activity}
     *
     * @param activity 指定的{@link Activity}实例
     */
    public void finishActivity(Activity activity) {
        synchronized (ActivityRouterManager.class){
            Iterator<WeakReference<Activity>> it = getActivityStack().iterator();
            while (it.hasNext()){
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    // 清理掉已经释放的activity
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 结束指定类名的所有{@link Activity}
     *
     * @param cls 指定的类的class
     */
    public void finishActivity(Class<?> cls) {
        synchronized (ActivityRouterManager.this){
            Iterator<WeakReference<Activity>> it = getActivityStack().iterator();
            while (it.hasNext()){
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                if (activity == null) {
                    // 清理掉已经释放的activity
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 指定的 {@link Activity} 实例是否存活
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(Activity activity){
        if (null == mActivityStack){
            LogUtil.i("mActivityStack == null when activityInstanceIsLive(activity)");
            return false;
        }
        return mActivityStack.contains(new WeakReference<Activity>(activity));
    }

    /**
     * 结束所有Activity
     */
    public void killAll() {
        synchronized (ActivityRouterManager.class) {
            Iterator<WeakReference<Activity>> iterator = getActivityStack().iterator();
            while (iterator.hasNext()){
                WeakReference<Activity> activityReference = iterator.next();
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            killAll();
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            LogUtil.e("Exit exception", e.getMessage());
        }
    }
}