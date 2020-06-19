package com.tang.webview.base;

import android.os.Bundle;

import com.tang.webview.R;
import com.tang.webview.WebContent;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: 正常的webView访问，没有js交互
 */
public class NormalWebViewFragment extends BaseWebViewFragment {

    /**
     * 初始化
     * @param url 需要访问的网址 最后交由父类{@link BaseWebViewFragment#web_url} 处理
     * @return
     */
    public static NormalWebViewFragment getInstance(String url){
        NormalWebViewFragment fragment = new NormalWebViewFragment();
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
        return R.layout.module_webview_fragment_normal_webview;
    }

    @Override
    public int getType() {
        return WebContent.TYPE_NORMAL;
    }
}
