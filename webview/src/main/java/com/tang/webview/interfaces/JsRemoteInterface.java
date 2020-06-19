package com.tang.webview.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.tang.base.utils.LogUtil;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: 和js远程交互接口类
 */
public class JsRemoteInterface {

    private Context mContext;

    public JsRemoteInterface(Context context) {
        this.mContext = context;
    }

    /**
     * 与js交互的实现方法
     * 加上注解，解决在4.2之后的安全性问题
     * @param cmd js指令key
     * @param params key的参数
     */
    @JavascriptInterface
    public void post(String cmd,String params){
        LogUtil.d("我是和js的交互："+cmd+ " ," + params);
    }
}
