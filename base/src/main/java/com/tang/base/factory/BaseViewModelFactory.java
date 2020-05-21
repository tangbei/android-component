package com.tang.base.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/17
 * Description: java类作用描述
 */
public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static BaseViewModelFactory mInstance;

    public static BaseViewModelFactory getInstance(){
        if (null == mInstance){
            mInstance = new BaseViewModelFactory();
        }
        return mInstance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
