package com.tang.webview.setting;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: webSettings的代理接口
 */
public interface AgentWebSettings<T extends WebSettings> {

    /**
     * 设置webView
     * @param webView
     * @return
     */
    AgentWebSettings toSetting(WebView webView);

    T getWebSettings();
}
