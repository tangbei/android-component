package com.tang.webview.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class SystemInfoUtil {

    /**
     * 判断当前是否是主进程，context 是 ApplicationContext
     */
    public static boolean inMainProcess(Context context, int pid) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context,pid);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static String getProcessName(Context context, int pid) {

        // ActivityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
