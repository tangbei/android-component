// IBinderPool.aidl
package com.tang.webview;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
     * 查找特定Binder方法
     */
    IBinder queryBinder(int binderCode);
}
