package com.tang.webview.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.tang.base.utils.LogUtil;
import com.tang.webview.IBinderPool;
import com.tang.webview.server.MainHandleRemoteService;

import java.util.concurrent.CountDownLatch;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class RemoteWebBinderPool {

    public static final int BINDER_WEB_AIDL = 1;
    private static volatile RemoteWebBinderPool instance;
    private CountDownLatch mConnectWebBinderPoolCountDownLatch;
    private IBinderPool mBinderPool;
    private Context mContext;

    private RemoteWebBinderPool(Context context){
        this.mContext = context.getApplicationContext();
        connectWebBinderPoolService();
    }

    public static RemoteWebBinderPool getInstance(Context context){
        if (null == instance){
            synchronized (RemoteWebBinderPool.class){
                if (null == instance){
                    instance = new RemoteWebBinderPool(context);
                }
            }
        }
        return instance;
    }

    public void connectWebBinderPoolService(){
        mConnectWebBinderPoolCountDownLatch = new CountDownLatch(1);
        //开启一个服务
        Intent intent = new Intent(mContext, MainHandleRemoteService.class);
        mContext.bindService(intent,mBinderPoolConnection,Context.BIND_AUTO_CREATE);
        try {
            //阻塞等待执行结束
            mConnectWebBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端
     */
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到服务端的binder
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //释放
            mConnectWebBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 通过{@link android.os.IBinder.DeathRecipient} 处理 {@link android.os.Binder}连接池死亡重联机制
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.w("连接池失效");
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBinderPool = null;
            //重连
            connectWebBinderPoolService();
        }
    };

    public IBinder queryBinder(int binderCode){
        IBinder iBinder = null;
        if (null != mBinderPool){
            try {
                iBinder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return iBinder;
    }

    /**
     * {@link IBinderPool.Stub}实现类
     * 目的是找到对应的{@link android.os.Binder}
     */
    public static class BinderPoolImpl extends IBinderPool.Stub {

        private Context mContext;

        public BinderPoolImpl(Context context) {
            this.mContext = context;
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BINDER_WEB_AIDL:
                    binder = new MainAidlInterface(mContext);
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
