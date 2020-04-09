/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: NetWorkConfigImpl
 * Author: tangbei
 * Date: 2020/4/9 11:21 AM
 * Description:
 * History:
 */
package com.tang.component.service;

import android.app.Application;
import android.content.Context;

import com.tang.component.BuildConfig;
import com.tang.component.network.base.INetWorkConfig;
import com.tang.component.network.base.NetWorkApi;

import java.io.File;

/**
 * Author: tangbei
 * Date: 2020/4/9 11:21 AM
 * Description: java类作用描述
 */
public class NetWorkConfigImpl implements INetWorkConfig {

    private Application mApplication;

    public NetWorkConfigImpl(Application application) {
        this.mApplication = application;
    }

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public Context getApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @Override
    public File getNetWorkCacheDir() {
        return mApplication.getCacheDir();
    }
}
