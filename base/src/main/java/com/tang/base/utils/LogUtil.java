package com.tang.base.utils;

import android.text.TextUtils;
import android.util.Log;

import com.tang.base.BuildConfig;

/**
 * 描述: log日志
 * 作者 : tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/6/12.
 */

public class LogUtil {

    /**
     * 日志开关标识 true为开，false为关
     */
    public static boolean isLog = true;

    /**
     * 日志tag
     */
    private static String TAG = "component_Tag";

    public static void setTAG(String TAG) {
        LogUtil.TAG = TAG;
    }

    public static void setIsLog(boolean isLog) {
        LogUtil.isLog = isLog;
    }

    public static boolean isIsLog() {
        return BuildConfig.DEBUG;
    }

    /**
     * debug日志输出
     * @param msg 打印信息
     */
    public static void d(String msg){
        if (!isLog) return;
        if (TextUtils.isEmpty(msg)){
            Log.d(TAG,"该log输出 为空 哦");
        }else {
            Log.d(TAG,getMsg(msg));
        }
    }

    /**
     * debug日志输出
     * @param objTag 输出tag
     * @param msg 打印信息
     */
    public static void d(Object objTag, String msg){
        if (!isLog) return;
        String tag;
        if (null == objTag){
            tag = TAG;
        }else if (objTag instanceof String){
            tag = (String) objTag;
        }else if (objTag instanceof Class){
            tag = ((Class) objTag).getSimpleName();
        } else {
            String[] split = objTag.getClass().getName().split("\\.");
            tag =split[split.length-1].split("\\$")[0];
        }
        if (TextUtils.isEmpty(msg)){
            Log.d(TAG.concat(tag),"该log输出 为空 哦");
        }else {
            Log.d(TAG.concat(tag),getMsg(msg));
        }
    }

    /**
     * 错误日志输出
     * @param msg 打印信息
     */
    public static void e(String msg){
        if (!isLog) return;
        if (TextUtils.isEmpty(msg)){
            Log.e(TAG,"该log输出 为空 哦");
        }else {
            Log.e(TAG,msg);
        }
    }

    /**
     * 错误日志输出
     * @param objTag 输出tag
     * @param msg 打印信息
     */
    public static void e(Object objTag, String msg){
        if (!isLog) return;
        String tag;
        if (null == objTag){
            tag = TAG;
        }else if (objTag instanceof String){
            tag = (String) objTag;
        }else if (objTag instanceof Class){
            tag = ((Class) objTag).getSimpleName();
        } else {
            String[] split = objTag.getClass().getName().split("\\.");
            tag =split[split.length-1].split("\\$")[0];
        }
        if (TextUtils.isEmpty(msg)){
            Log.e(TAG.concat(tag),"该log输出 为空 哦");
        }else {
            Log.e(TAG.concat(tag),getMsg(msg));
        }
    }

    /**
     * i信息日志输出
     * @param msg 打印信息
     */
    public static void i(String msg){
        if (!isLog) return;
        if (TextUtils.isEmpty(msg)){
            Log.i(TAG,"该log输出 为空 哦");
        }else {
            Log.i(TAG,msg);
        }
    }

    /**
     * i信息日志输出
     * @param objTag 输出tag
     * @param msg 打印信息
     */
    public static void i(Object objTag, String msg){
        if (!isLog) return;
        String tag;
        if (null == objTag){
            tag = TAG;
        }else if (objTag instanceof String){
            tag = (String) objTag;
        }else if (objTag instanceof Class){
            tag = ((Class) objTag).getSimpleName();
        } else {
            String[] split = objTag.getClass().getName().split("\\.");
            tag =split[split.length-1].split("\\$")[0];
        }
        if (TextUtils.isEmpty(msg)){
            Log.i(TAG.concat(tag),"该log输出 为空 哦");
        }else {
            Log.i(TAG.concat(tag),getMsg(msg));
        }
    }

    /**
     * 警告信息日志输出
     * @param msg 打印信息
     */
    public static void w(String msg){
        if (!isLog) return;
        if (TextUtils.isEmpty(msg)){
            Log.w(TAG,"该log输出 为空 哦");
        }else {
            Log.w(TAG,getMsg(msg));
        }
    }

    /**
     * 警告信息日志输出
     * @param objTag 输出tag
     * @param msg 打印信息
     */
    public static void w(Object objTag, String msg){
        if (!isLog) return;
        String tag;
        if (null == objTag){
            tag = TAG;
        }else if (objTag instanceof String){
            tag = (String) objTag;
        }else if (objTag instanceof Class){
            tag = ((Class) objTag).getSimpleName();
        } else {
            String[] split = objTag.getClass().getName().split("\\.");
            tag =split[split.length-1].split("\\$")[0];
        }
        if (TextUtils.isEmpty(msg)){
            Log.w(TAG.concat(tag),"该log输出 为空 哦");
        }else {
            Log.w(TAG.concat(tag),getMsg(msg));
        }
    }

    private static String getMsg(String msg){
        msg = msg.trim();
        int index = 0;
        int maxLength = 3500;
        String sub;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;
            return sub.trim();
        }
        return "";
    }
}
