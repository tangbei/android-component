/**
 * Copyright (C), 2019-2020
 * FileName: MvvmNetworkObserver
 * Author: tangbei
 * Date: 2020/4/15 1:30 PM
 * Description:
 */
package com.tang.base.model;

/**
 * Author: tang
 * e-mail: itangbei@sina.com
 * Date: 2020/4/15
 * Description: 请求回调的处理结果
 */
public interface NetworkObserver<T> {

    /**
     * 成功
     * @param t
     */
    void onSuccess(T t);

    /**
     * 失败
     * @param e
     */
    void onFailure(Throwable e);

}
