/**
 * Copyright (C), 2019-2020
 * FileName: INetWorkConfig
 * Author: tangbei
 * Date: 2020/4/8 5:20 PM
 * Description:
 * History:
 */
package com.tang.component.network.base;

import android.app.Application;
import android.content.Context;

import java.io.File;

/**
 * @ClassName: INetWorkConfig
 * @Description: 网络请求配置
 * @Author: tangbei
 * @Date: 2020/4/8 5:20 PM
 */
public interface INetWorkConfig {

    /**
     * 是否打印网络日志
     * @return
     */
    boolean isDebug();

    /**
     * 获取visionCode
     * @return
     */
    String getAppVersionCode();

    /**
     * 获取当前的application
     * @return
     */
    Context getApplicationContext();

    /**
     * 网络请求缓存路径
     * @return
     */
    File getNetWorkCacheDir();
}