package com.tang.component.proxytest;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/21
 * Description: java类作用描述
 */
public class ShopPhoneProxy implements IShopPhone {

    private IShopPhone iShopPhone;
    public ShopPhoneProxy(ShopPhone shopPhone) {
        this.iShopPhone = shopPhone;
    }


    @Override
    public void shopPhone(String str) {
        System.out.println("我是代理类----->start");
        this.iShopPhone.shopPhone(str);
        System.out.println("我是代理类----->end");
    }
}
