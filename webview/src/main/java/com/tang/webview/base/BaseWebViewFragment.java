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

import com.tang.base.utils.LogUtil;
import com.tang.webview.R;
import com.tang.webview.WebContent;
import com.tang.webview.action.CommandDispatcher;
import com.tang.webview.interfaces.Action;
import com.tang.webview.interfaces.BaseWebViewCallBack;
import com.tang.webview.interfaces.WebViewLifeCycle;
import com.tang.webview.setting.DefaultWebViewLifeCycleImpl;
import com.tang.webview.setting.MainLooper;
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
        //注册监听
        mWebView.registerWebViewCallback(this);
        LogUtil.w("baseWebView:","初始化webwiew进程为："+ Thread.currentThread().getId());
        CommandDispatcher.getInstance().initAidlConnect(getContext(), new Action() {
            @Override
            public void call(Object o) {
                MainLooper.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadUrl();
                    }
                });
            }
        });
    }

    protected void loadUrl(){
        LogUtil.w("baseWebView:","打开页面时的webwiew进程为："+ Thread.currentThread().getId());
        mWebView.loadUrl(web_url);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.dispatchEvent("pageResume",null);
        mLifeCycle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.dispatchEvent("pagePause",null);
        mLifeCycle.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.dispatchEvent("pageStop",null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.dispatchEvent("pageDestroy",null);
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

    @Override
    public void exec(Context context, WebView webView, int level, String cmd, String params) {
        CommandDispatcher.getInstance().exec(context,webView,level,cmd,params,getDispatcherCallback());
    }

    private CommandDispatcher.DispatcherCallback getDispatcherCallback(){
        return null;
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
