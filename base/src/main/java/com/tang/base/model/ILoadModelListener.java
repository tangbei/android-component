package com.tang.base.model;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/16
 * Description: 网络请求加载的监听
 */
public interface ILoadModelListener<T> {

    void onLoadFinish(MvvmBaseModel model, T data);

    void onLoadFail(MvvmBaseModel model, String prompt);
}
