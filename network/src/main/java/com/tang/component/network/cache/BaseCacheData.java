package com.tang.component.network.cache;

import java.io.Serializable;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/25
 * Description: java类作用描述
 */
public class BaseCacheData<T> implements Serializable {

    public long updateTimeInMills;
    public T data;
}
