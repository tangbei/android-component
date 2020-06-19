package com.tang.webview.base;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tang.webview.R;
import com.tang.webview.WebContent;
import com.tang.webview.interfaces.BaseWebViewCallBack;
import com.tang.webview.interfaces.WebViewLifeCycle;
import com.tang.webview.setting.DefaultWebViewLifeCycleImpl;
import com.tang.webview.view.BaseWebView;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: java类作用描述
 */
public abstract class BaseWebViewFragment extends Fragment implements BaseWebViewCallBack {

    protected String web_url = "";
    private BaseWebView mWebView;
    protected Context mContext;
    private WebViewLifeCycle mLifeCycle;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mContext){
            mContext = getContext();
        }
        Bundle bundle = getArguments();
        if (null != bundle){
            web_url = bundle.getString(WebContent.INTENT_TAG_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(),container,false);
        mWebView = view.findViewById(R.id.webView);
        return view;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext() == null ? mContext : super.getContext();
    }

    /**
     * 获取布局
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifeCycle = new DefaultWebViewLifeCycleImpl(mWebView);
        mWebView.registerWebViewCallback(this);
        loadUrl();
    }

    protected void loadUrl(){
        mWebView.loadUrl(web_url);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.dispatchEvent("pageResume");
        mLifeCycle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.dispatchEvent("pagePause");
        mLifeCycle.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.dispatchEvent("pageStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.dispatchEvent("pageDestroy");
        mLifeCycle.onDestroy();
    }

    @Override
    public int getType() {
        return WebContent.TYPE_NORMAL;
    }

    @Override
    public boolean overrideUrlLoading(WebView webView, String url) {
        return false;
    }

    @Override
    public void pageStarted(String url) {

    }

    @Override
    public void pageFinished(String url) {

    }

    @Override
    public void onError() {

    }

    /**
     * 返回操作
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return onBackHandle();
        }
        return false;
    }

    private boolean onBackHandle() {
        if (null != mWebView){
            if (mWebView.canGoBack()){
                mWebView.goBack();
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
