/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: BaseObserver
 * Author: tangbei
 * Date: 2020/4/9 10:57 AM
 * Description:
 * History:
 */
package com.tang.component.network.observer;

import com.tang.base.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author: tangbei
 * Date: 2020/4/9 10:57 AM
 * Description: observer请求结果处理
 */
public class BaseObserver<T> implements Observer<T> {

    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {
        LogUtil.d(TAG,"onSubscribe回调");
    }

    @Override
    public void onNext(T t) {
        LogUtil.d(TAG,"onNext回调");
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.d(TAG,"onError回调："+e.getMessage());
    }

    @Override
    public void onComplete() {
        LogUtil.d(TAG,"onComplete回调");
    }
}
