package com.tang.component.executor;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/29
 * Description: java类作用描述
 */
public class DefaultExecutorSupplier {

    private static DefaultExecutorSupplier mInstance;

    private static DefaultExecutorSupplier getInstance(){
        if (null == mInstance){
            synchronized (DefaultExecutorSupplier.class){
                if (null == mInstance){
                    mInstance = new DefaultExecutorSupplier();
                }
            }
        }
        return mInstance;
    }

    private DefaultExecutorSupplier() {

    }
}
