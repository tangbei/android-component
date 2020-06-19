package com.tang.webview;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: java类作用描述
 */
public class WebContent {


    /**
     * 传递url标识
     */
    public static final String INTENT_TAG_URL = "web_url";

    public static final String CONTENT_SCHEME = "file:///android_asset/";

    /**
     * 正常跳转,进入{@link com.tang.webview.base.NormalWebViewFragment}
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 自定义交互，进入{@link com.tang.webview.base.InterfaceWebViewFragment}
     */
    public static final int TYPE_INTERFACE = 1;
}
