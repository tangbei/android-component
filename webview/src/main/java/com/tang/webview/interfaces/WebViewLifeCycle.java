package com.tang.webview.interfaces;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: webView代理生命周期
 */
public interface WebViewLifeCycle {

    void onResume();

    void onPause();

    void onDestroy();

}
