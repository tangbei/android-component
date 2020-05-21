package com.tang.component.proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/21
 * Description: java类作用描述
 */
public class ProxyFactory {

    private Object mTarget;

    public ProxyFactory(Object mTarget) {
        this.mTarget = mTarget;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(mTarget.getClass().getClassLoader(), mTarget.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(mTarget,args);
            }
        });
    }
}
