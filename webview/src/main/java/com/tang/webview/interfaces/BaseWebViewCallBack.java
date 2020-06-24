package com.tang.webview.interfaces;

import android.content.Context;
import android.webkit.WebView;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: webView同一回调处理
 * 所有涉及到webView交互的都必须实现这个callback
 */
public interface BaseWebViewCallBack {

    /**
     * 类型 普通url打开，自定义交互url打开方式
     */
    int getType();

    /**
     * 页面重定向
     * @param webView {@link WebView}
     * @param url 重定向地址
     * @return
     */
    boolean overrideUrlLoading(WebView webView,String url);

    /**
     * 页面开始加载
     */
    void pageStarted(String url);

    /**
     * 页面加载结束
     * @param url
     */
    void pageFinished(String url);

    /**
     * 页面加载出错
     */
    void onError();

    /**
     * js和native交互指令
     * @param context 上下文对象
     * @param cmd 指令
     * @param params 内容参数
     */
    void exec(Context context, WebView webView, int level, String cmd,String params);
}
