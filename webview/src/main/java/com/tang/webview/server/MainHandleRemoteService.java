package com.tang.webview.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import androidx.annotation.Nullable;

import com.tang.base.utils.LogUtil;
import com.tang.webview.aidl.RemoteWebBinderPool;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/2
 * Description: java类作用描述
 */
public class MainHandleRemoteService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int pid = Process.myPid();
        Binder mBinderPool = new RemoteWebBinderPool.BinderPoolImpl(this);
        LogUtil.d(String.format("MainHandleRemoteService: %s","当前进程id为："+pid+"-----客户端与服务端连接成功，服务端返回"+mBinderPool.getClass().getName().toString()));
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
