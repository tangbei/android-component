package com.tang.base.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.tang.base.utils.LogUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class SingleLiveData<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveData";

    private final AtomicBoolean mBoolean = new AtomicBoolean(false);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        if (hasActiveObservers()){
            LogUtil.w(TAG,"Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (mBoolean.compareAndSet(true,false)){
                    observer.onChanged(t);
                }
            }
        });
    }

    @Override
    public void setValue(T value) {
        mBoolean.set(true);
        super.setValue(value);
    }

    public void call(){
        setValue(null);
    }
}
