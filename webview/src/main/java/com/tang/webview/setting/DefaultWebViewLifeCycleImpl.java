package com.tang.webview.setting;

import android.os.Looper;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.tang.webview.interfaces.WebViewLifeCycle;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: webView生命周期实现类
 */
public class DefaultWebViewLifeCycleImpl implements WebViewLifeCycle {

    private WebView mWebView;

    public DefaultWebViewLifeCycleImpl(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void onResume() {
        if (null != this.mWebView){
            this.mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (null != this.mWebView){
            this.mWebView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (null != this.mWebView){
            clearWebView(this.mWebView);
        }
    }

    /**
     * {@link WebView}销毁操作
     * @param webView
     */
    private void clearWebView(WebView webView) {
        if (null == webView){
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()){
            //如果不是运行在主线程，则return
            return;
        }
        webView.stopLoading();
        if (null != webView.getHandler()){
            //清除handler
            webView.getHandler().removeCallbacksAndMessages(null);
        }
        webView.removeAllViews();
        ViewGroup viewGroup = null;
        if ((viewGroup = (ViewGroup) webView.getParent()) != null){
            //如果webView还在，则删除当前view
            viewGroup.removeView(webView);
        }
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.setTag(null);
        webView.clearHistory();
        webView.destroy();
        webView = null;
    }
}
