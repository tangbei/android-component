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

import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.service.NetWorkConfigImpl;

/**
 * Author: tangbei
 * Date: 2020/4/9 12:36 PM
 * Description: Application
 */
public class ComponentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络请求框架
        CommonNetWorkApi.init(new NetWorkConfigImpl(this));
    }
}
