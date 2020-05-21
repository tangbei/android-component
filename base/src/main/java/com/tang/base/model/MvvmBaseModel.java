/**
 * Copyright (C), 2019-2020
 * FileName: MvvmBaseModel
 * Author: tangbei
 * Date: 2020/4/15 1:29 PM
 * Description:
 */
package com.tang.base.model;

import androidx.annotation.CallSuper;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: java类作用描述 
 */
public abstract class MvvmBaseModel<T> implements MvvmNetworkObserver<T> {

    //订阅管理器
    private CompositeDisposable compositeDisposable;
    //网络监听的弱引用队列
    private ReferenceQueue<ILoadModelListener> mReferenceQueue;
    //安全的并发线程队列
    private ConcurrentLinkedQueue<WeakReference<ILoadModelListener>> mWeakListenerArrayList;

    public MvvmBaseModel() {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
    }

    /**
     * 注册网络请求后的监听
     * @param listener
     */
    private void register(ILoadModelListener listener){
        if (null == listener) return;
        synchronized (this){
            //每次注册的时候 清理已经被系统回收的对象
            Reference<? extends ILoadModelListener> referenceListener = null;
            while ((referenceListener = mReferenceQueue.poll()) != null){
                mWeakListenerArrayList.remove(referenceListener);
            }
            for (WeakReference<ILoadModelListener> weakListener :  mWeakListenerArrayList){
                ILoadModelListener itemListener = weakListener.get();
                if (itemListener == listener){
                    return;
                }
            }
            WeakReference<ILoadModelListener> weakListener = new WeakReference<>(listener,mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }
    }

    /**
     * 取消网络请求后的监听
     */
    private void unRegister(ILoadModelListener listener){
        if (null == listener) return;
        synchronized (this){
            for (WeakReference<ILoadModelListener> weakListener : mWeakListenerArrayList){
                ILoadModelListener itemListener = weakListener.get();
                if (listener == itemListener){
                    mWeakListenerArrayList.remove(weakListener);
                    break;
                }
            }
        }
    }

    /**
     * 每次请求时，添加订阅
     * @param d
     */
    public void addDisposable(Disposable d){
        if (null == d){
            return;
        }
        if (null == compositeDisposable){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    /**
     * ViewModel销毁时，解除请求的订阅
     */
    @CallSuper
    public void cancel() {
        //如果订阅管理器不为空，并且当前的订阅还未解除，则dispose
        if (null != compositeDisposable && !compositeDisposable.isDisposed()){
            //订阅取消
            compositeDisposable.dispose();
        }
    }

    /**
     * 网络请求刷新
     */
    public abstract void refresh();

    /**
     * 网络加载操作
     */
    protected abstract void load();

    protected void loadSuccess(T data){
        synchronized (this){
            for (WeakReference<ILoadModelListener> weakListener : mWeakListenerArrayList){
                if (weakListener.get() instanceof ILoadModelListener){
                    ILoadModelListener itemListener = weakListener.get();
                    if (null != itemListener){
                        itemListener.onLoadFinish(this,data);
                    }
                }
            }
        }
    }

    protected void loadFail(String errorMessage){
        synchronized (this){
            for (WeakReference<ILoadModelListener> weakListener : mWeakListenerArrayList){
                if (weakListener.get() instanceof ILoadModelListener){
                    ILoadModelListener itemListener = weakListener.get();
                    if (null != itemListener){
                        itemListener.onLoadFail(this,errorMessage);
                    }
                }
            }
        }
    }
}
