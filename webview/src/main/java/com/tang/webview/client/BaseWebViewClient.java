package com.tang.webview.client;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tang.base.utils.LogUtil;
import com.tang.webview.R;
import com.tang.webview.interfaces.BaseWebViewCallBack;
import com.tang.webview.view.BaseWebView;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: java类作用描述
 */
public class BaseWebViewClient extends WebViewClient {

    private static final String TAG = "BaseWebViewClient:";
    private static final String CONTENT_SCHEME = "file:///android_asset/";
    private static final String SCHEME_SMS = "sms:";
    private boolean mTouchByUser;
    private BaseWebView mWebView;
    private BaseWebViewCallBack mWebViewCallBack;
    private Map<String,String> mHeaders;
    private boolean isReady;

    public BaseWebViewClient(WebView webView) {
        this.mWebView = (BaseWebView) webView;
    }

    public BaseWebViewCallBack getWebViewCallBack() {
        return mWebViewCallBack;
    }

    public void setWebViewCallBack(BaseWebViewCallBack webViewCallBack) {
        this.mWebViewCallBack = webViewCallBack;
    }

    public boolean ismTouchByUser() {
        return mTouchByUser;
    }

    public void setTouchByUser(boolean touchByUser) {
        this.mTouchByUser = touchByUser;
    }

    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    /**
     * url重定向会执行此方法以及点击页面某些链接也会执行此方法
     * @param view
     * @param request
     * @return {@code true}表示当前url已经加载完成，即使url还会重定向都不会再进行加载
     *         {@code false}表示url默认由系统处理，该重定向还是重定向，直到加载完成
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        LogUtil.d(TAG,"shouldOverrideUrlLoading url:"+request.getUrl());
        //当前链接的重定向是否通过点击行为来判断
        if (ismTouchByUser()){
            return super.shouldOverrideUrlLoading(view, request);
        }
        //如果重定向链接和当前链接一样，则表示刷新
        if (mWebView.getUrl().equals(request.getUrl().toString())){
            return super.shouldOverrideUrlLoading(view, request);
        }
        if (handleLinked(request.getUrl().toString())){
            return true;
        }
        if (null != mWebViewCallBack && mWebViewCallBack.overrideUrlLoading(view,request.getUrl().toString())){
            return true;
        }
        //控制页面中点击新开的链接在当前webView中打开
        view.loadUrl(request.getUrl().toString(),mHeaders);
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.d(TAG,"shouldOverrideUrlLoading url:"+url);
        //当前链接的重定向是否通过点击行为来判断
        if (ismTouchByUser()){
            return super.shouldOverrideUrlLoading(view, url);
        }
        //如果重定向链接和当前链接一样，则表示刷新
        if (mWebView.getUrl().equals(url)){
            return super.shouldOverrideUrlLoading(view, url);
        }
        if (handleLinked(url)){
            return true;
        }
        if (null != mWebViewCallBack && mWebViewCallBack.overrideUrlLoading(view,url)){
            return true;
        }
        //控制页面中点击新开的链接在当前webView中打开
        view.loadUrl(url,mHeaders);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LogUtil.d(TAG,"onPageStarted url:" + url);
        if (null != mWebViewCallBack){
            mWebViewCallBack.pageStarted(url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.d(TAG,"onPageFinished url:" + url);
        //如果地址不为空并且是本地地址
        if (!TextUtils.isEmpty(url) && url.startsWith(CONTENT_SCHEME)){
            isReady = true;
        }
        if (null != mWebViewCallBack){
            mWebViewCallBack.pageFinished(url);
        }
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return null;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtil.w(TAG,"webView error" + errorCode + " + " + description);
        if (null != mWebViewCallBack){
            mWebViewCallBack.onError();
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        String channel = "";
        if (!TextUtils.isEmpty(channel) && channel.equals("play.google.com")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mWebView.getContext());
            String message = mWebView.getContext().getString(R.string.module_webview_ssl_error);
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = mWebView.getContext().getString(R.string.module_webview_ssl_error_not_trust);
                    break;
                case SslError.SSL_EXPIRED:
                    message = mWebView.getContext().getString(R.string.module_webview_ssl_error_expired);
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = mWebView.getContext().getString(R.string.module_webview_ssl_error_mismatch);
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = mWebView.getContext().getString(R.string.module_webview_ssl_error_not_valid);
                    break;
            }
            message += mWebView.getContext().getString(R.string.module_webview_ssl_error_continue_open);

            builder.setTitle(R.string.module_webview_ssl_error);
            builder.setMessage(message);
            builder.setPositiveButton(R.string.module_webview_continue_open, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton(R.string.module_webview_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            handler.proceed();
        }
    }

    /**
     * 支持电话、短信、邮件、地图跳转，跳转的都是手机系统自带的应用
     * @param url 当前跳转的url
     */
    private boolean handleLinked(String url) {
        if (url.startsWith(WebView.SCHEME_TEL)
                || url.startsWith(SCHEME_SMS)
                || url.startsWith(WebView.SCHEME_MAILTO)
                || url.startsWith(WebView.SCHEME_GEO)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mWebView.getContext().startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
                ignored.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
