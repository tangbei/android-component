package com.tang.component.proxytest;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/21
 * Description: java类作用描述
 */
public class User {

    @Test
    public void main(){
        /*在大陆买手机*/
        ShopPhone shopPhone = new ShopPhone();
        shopPhone.shopPhone("在大陆买了一个iPhone X，一共花了8888元");
        /*在香港买手机*/
        /*ShopPhoneProxy proxyShopPhone = new ShopPhoneProxy(shopPhone);
        proxyShopPhone.shopPhone("通过代购，在香港买了一个iphone X，一共花了6666元");*/

        /*IShopPhone proxy = (IShopPhone) Proxy.newProxyInstance(shopPhone.getClass().getClassLoader(), shopPhone.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("-----动态代理 start----");
                Object returnValue = method.invoke(shopPhone, args);
                System.out.println("-----动态代理 end----");
                return returnValue;
            }
        });*/
        IShopPhone proxy = (IShopPhone) new ProxyFactory(shopPhone).getProxyInstance();
        proxy.shopPhone("通过代理代购，在香港买了一个iphone X，一共花了6666元");
    }
}
