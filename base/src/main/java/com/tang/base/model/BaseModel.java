/**
 * Copyright (C), 2019-2020
 * FileName: MvvmBaseModel
 * Author: tangbei
 * Date: 2020/4/15 1:29 PM
 * Description:
 */
package com.tang.base.model;

import android.app.Application;

import androidx.annotation.NonNull;
import com.tang.component.network.cache.Cache2;
import com.tang.component.network.cache.CacheType2;
import com.tang.component.network.cache.LruCache;
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
public abstract class BaseModel<T> implements NetworkObserver<T>,IModel {

    //订阅管理器
    private CompositeDisposable compositeDisposable;
    //网络监听的弱引用队列
    private ReferenceQueue<ILoadModelListener> mReferenceQueue;
    //安全的并发线程队列
    private ConcurrentLinkedQueue<WeakReference<ILoadModelListener>> mWeakListenerArrayList;
    //缓存的key
    private String mCacheJsonKey;
    //缓存数据
    private Cache2<String,T> mCache;
    private Cache2.Factory mFactory;


    public BaseModel(){
        this(null,null);
    }

    /**
     * 初始化对象 如果需要缓存数据，则当前的 {@link Application} 不能为空
     * @param application
     * @param cacheJsonKey 当前实体缓存的{@code key}
     */
    protected BaseModel(Application application,String cacheJsonKey) {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        this.mCacheJsonKey = cacheJsonKey;
        if (null != application){
            this.mFactory = getFactory(application);
            if (null == mCache){
                this.mCache = mFactory.build(CacheType2.RETROFIT_SERVICE_CACHE);
            }
        }
    }

    private Cache2.Factory getFactory(final Application application) {
        return mFactory == null ? new Cache2.Factory() {
            @NonNull
            @Override
            public Cache2 build(CacheType2 type) {
                return new LruCache(type.calculateCacheSize(application));
            }
        } : mFactory;
    }

    /**
     * 注册网络请求后的监听
     * @param listener
     */
    public void register(ILoadModelListener listener){
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
    public void unRegister(ILoadModelListener listener){
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
     * 网络请求刷新
     */
    public abstract void refresh();

    /**
     * 网络加载操作
     */
    protected abstract void load();

    /**
     * 缓存策略
     * @return
     */
    protected boolean isNeedToUpdate(){
        return false;
    }

    /**
     * {@link BaseModel#load()} 请求数据
     * 先获取缓存信息{@code mCachePreferenceKey}，如果缓存信息存在
     * 并且{@link BaseModel#isNeedToUpdate()} 需要更新，则 {@link BaseModel#load()}
     */
    public void getCacheDataAndLoad(){
        if (null != mCacheJsonKey){
            T cacheData = mCache.get(mCacheJsonKey);
            if (null != cacheData){
                onSuccess(cacheData);
                if (isNeedToUpdate()) {
                    load();
                }
                return;
            }
        }
        load();
    }

    /**
     * 回调加载成功的数据
     * @param data
     */
    protected void loadSuccess(T data){
        synchronized (this){
            for (WeakReference<ILoadModelListener> weakListener : mWeakListenerArrayList){
                if (weakListener.get() instanceof ILoadModelListener){
                    ILoadModelListener itemListener = weakListener.get();
                    if (null != itemListener){
                        //如果缓存不为空
                        if (null != mCacheJsonKey){
                            mCache.put(mCacheJsonKey,data);
                        }
                        itemListener.onLoadFinish(this,data);
                    }
                }
            }
        }
    }

    /**
     * 回调加载失败的数据
     * @param errorMessage
     */
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

    /**
     * ViewModel销毁时，解除请求的订阅
     */
    @Override
    public void onCleared() {
        if (null != mCache){
            mCache.clear();
            mCache = null;
        }
        if (null != mFactory){
            mFactory = null;
        }
        //如果订阅管理器不为空，并且当前的订阅还未解除，则dispose
        if (null != compositeDisposable && !compositeDisposable.isDisposed()){
            //订阅取消
            compositeDisposable.dispose();
        }
    }
}
