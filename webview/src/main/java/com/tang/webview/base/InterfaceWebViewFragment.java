package com.tang.webview.base;

import android.os.Bundle;

import com.tang.webview.R;
import com.tang.webview.WebContent;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: java类作用描述
 */
public class InterfaceWebViewFragment extends BaseWebViewFragment {

    /**
     * 自定义webViewFragment访问路径
     * @param url 请求地址
     * @return
     */
    public static InterfaceWebViewFragment newInstance(String url){
        InterfaceWebViewFragment fragment = new InterfaceWebViewFragment();
        fragment.setArguments(getBundle(url));
        return fragment;
    }

    private static Bundle getBundle(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WebContent.INTENT_TAG_URL,url);
        return bundle;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.module_webview_fragment_interface_webview;
    }

    @Override
    public int getType() {
        return WebContent.TYPE_INTERFACE;
    }
}
