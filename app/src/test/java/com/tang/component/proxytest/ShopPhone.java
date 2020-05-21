package com.tang.component.proxytest;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/21
 * Description: java类作用描述
 */
public class ShopPhone implements IShopPhone {
    @Override
    public void shopPhone(String str) {
        System.out.println("我是实现类---->"+str);
    }
}
