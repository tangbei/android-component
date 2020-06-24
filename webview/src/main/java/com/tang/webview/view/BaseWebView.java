package com.tang.webview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.tang.base.utils.LogUtil;
import com.tang.webview.WebContent;
import com.tang.webview.client.BaseWebViewClient;
import com.tang.webview.interfaces.BaseWebViewCallBack;
import com.tang.webview.interfaces.CallbackWrapperBase;
import com.tang.webview.interfaces.CallbackWrapperM;
import com.tang.webview.interfaces.JsRemoteInterface;
import com.tang.webview.setting.WebDefaultSettingsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: 自定义webView
 */
public class BaseWebView extends WebView {

    private static final String TAG = "BaseWebView:";
    protected Context mContext;
    private BaseWebViewClient mWebViewClient;
    private JsRemoteInterface mJsRemoteInterface;
    private ActionMode.Callback mCustomCallback;
    private BaseWebViewCallBack mWebViewCallBack;

    public BaseWebView(Context context) {
        super(context);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context){
        this.mContext = context;
        WebDefaultSettingsManager.getInstance().toSetting(this);
        mWebViewClient = new BaseWebViewClient(this);
        setWebViewClient(mWebViewClient);

        //web和native交互
        if (null == mJsRemoteInterface){
            mJsRemoteInterface = new JsRemoteInterface(context);
            mJsRemoteInterface.setAidlCommand(new JsRemoteInterface.AidlCommand() {
                @Override
                public void exec(Context context, String cmd, String params) {
                    LogUtil.d(TAG,"cmd:"+cmd + ",params:" + params);
                    if (null != mWebViewCallBack){
                        mWebViewCallBack.exec(mContext, BaseWebView.this, mWebViewCallBack.getType(),cmd,params);
                    }
                }
            });
        }
        setJavascriptInterface(mJsRemoteInterface);
    }

    /**
     * 注册回调
     * @param callBack
     */
    public void registerWebViewCallback(BaseWebViewCallBack callBack){
        this.mWebViewCallBack = callBack;
        if (null != mWebViewClient){
            mWebViewClient.setWebViewCallBack(callBack);
        }
    }

    /**
     * 重写{@link ActionMode}
     * @param callback
     * @return
     */
    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ViewParent parent = getParent();
        if (null != parent){
            return parent.startActionModeForChild(this,warpCallback(callback));
        }
        return super.startActionMode(callback);
    }

    /**
     * 重写{@link ActionMode}
     * @param callback
     * @param type
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ViewParent parent = getParent();
        if (null != parent){
            return parent.startActionModeForChild(this,warpCallback(callback),type);
        }
        return null;
    }

    /**
     * 重写{@link ActionMode.Callback}
     * @param callback
     * @return
     */
    private ActionMode.Callback warpCallback(ActionMode.Callback callback){
        if (null != mCustomCallback){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return new CallbackWrapperM(mCustomCallback,callback);
            }else {
                return new CallbackWrapperBase(mCustomCallback,callback);
            }
        }
        return callback;
    }

    public void setCustomCallback(ActionMode.Callback customCallback) {
        this.mCustomCallback = customCallback;
    }

    /**
     * webView添加注册js接口回调监听
     * 就是给js添加交互指令
     * js端调用方式 window.webview
     * @param remoteInterface 该类实现js要调用的方法，并实例化传递给webView
     */
    @SuppressLint({"AddJavascriptInterface","setJavascriptInterface"})
    public void setJavascriptInterface(JsRemoteInterface remoteInterface){
        addJavascriptInterface(remoteInterface,"webview");
    }

    /**
     * 设置html内容加载
     */
    public void setContent(String htmlContent){
        try {
            loadDataWithBaseURL(WebContent.CONTENT_SCHEME,htmlContent,"text/html","UTF-8",null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载url
     * @param url 访问地址
     */
    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        LogUtil.d(TAG,"load url:" + url);
        resetAllStateInternal(url);
    }

    /**
     * 加载url
     * @param url 访问地址
     * @param additionalHttpHeaders 地址添加的请求头
     */
    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
        LogUtil.d(TAG,"load url:" + url);
        resetAllStateInternal(url);
    }

    /**
     * 重置状态
     * @param url
     */
    private void resetAllStateInternal(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("javascript:")){
            return;
        }
        resetAllState();
    }

    /**
     * 加载url时重置touch状态
     */
    private void resetAllState() {
        if (null != mWebViewClient){
            mWebViewClient.setTouchByUser(false);
        }
    }

    /**
     * 监听屏幕点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (null != mWebViewClient){
                    mWebViewClient.setTouchByUser(true);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 加载js
     * @param trigger
     */
    public void loadJs(String trigger){
        if (!TextUtils.isEmpty(trigger)){
            //Android4.4之后通过evaluate调用js
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                evaluateJavascript(trigger,null);
            }else {
                loadUrl(trigger);
            }
        }
    }

    public void loadJs(String cmd,Object params){
        String trigger = "javascript:" + cmd + "(" + new Gson().toJson(params) + ")";
        loadJs(trigger);
    }

    public void handleCallback(String json){
        String trigger = "javascript:" + "dj.callback" + "(" + json + ")";
        //Android4.4之后通过evaluate调用js
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(trigger,null);
        }else {
            loadUrl(trigger);
        }
    }

    public void dispatchEvent(String name){
        Map<String,String> params = new HashMap<>(1);
        params.put("name",name);
        loadJs("dj.dispatchEvent",params);
    }

    public void dispatchEvent(Map params){
        loadJs("dj.dispatchEvent",params);
    }

    @Override
    public void goBack() {
        super.goBack();
    }
}
