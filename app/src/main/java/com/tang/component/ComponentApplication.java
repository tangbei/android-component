/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: ComponentApplication
 * Author: tangbei
 * Date: 2020/4/9 12:36 PM
 * Description:
 * History:
 */
package com.tang.component;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.router.ActivityRouterManager;
import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.service.NetWorkConfigImpl;

/**
 * Author: tangbei
 * Date: 2020/4/9 12:36 PM
 * Description: Application
 */
public class ComponentApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();
        ARouter.init(this);

        //初始化网络请求框架
        CommonNetWorkApi.init(new NetWorkConfigImpl(this));
    }
}
