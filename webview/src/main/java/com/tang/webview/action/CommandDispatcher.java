package com.tang.webview.action;

import android.content.Context;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.tang.base.utils.LogUtil;
import com.tang.webview.IWebAidlCallback;
import com.tang.webview.IWebAidlInterface;
import com.tang.webview.WebContent;
import com.tang.webview.aidl.RemoteWebBinderPool;
import com.tang.webview.interfaces.Action;
import com.tang.webview.interfaces.ResultBack;
import com.tang.webview.setting.MainLooper;
import com.tang.webview.utils.SystemInfoUtil;
import com.tang.webview.view.BaseWebView;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: {@link com.tang.webview.view.BaseWebView} 所有指令分发处理类
 */
public class CommandDispatcher {

    private static final String TAG = "CommandDispatcher:";
    private static CommandDispatcher instance;
    private IWebAidlInterface webAidlInterface;
    private Gson gson;

    private CommandDispatcher(){
        gson = new Gson();
    }

    public static CommandDispatcher getInstance(){
        if (null == instance){
            synchronized (CommandDispatcher.class){
                if (null == instance){
                    instance = new CommandDispatcher();
                }
            }
        }
        return instance;
    }

    public void initAidlConnect(final Context context, final Action action){
        if (null != webAidlInterface){
            if (null != action){
                action.call(null);
            }
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.w("AIDL","begin to connect with main process");
                RemoteWebBinderPool binderPool = RemoteWebBinderPool.getInstance(context);
                IBinder iBinder = binderPool.queryBinder(RemoteWebBinderPool.BINDER_WEB_AIDL);
                webAidlInterface = IWebAidlInterface.Stub.asInterface(iBinder);
                LogUtil.w("AIDL","connect success with main process");
                if (null != action){
                    action.call(null);
                }
            }
        }).start();
    }

    /**
     * 指令分发
     * @param context
     * @param webView
     * @param commandLevel
     * @param cmd
     * @param params
     */
    public void exec(Context context, WebView webView,int commandLevel, String cmd, String params, DispatcherCallback dispatcherCallback){
        LogUtil.w(TAG,"cmd:"+ cmd + ",params:" + params);
        try {
            if (CommandsManager.getInstance().checkUICommand(commandLevel,cmd)){
                execUI(context,webView,commandLevel,cmd,params,dispatcherCallback);
            }else {
                execNonUI(context,webView,commandLevel,cmd,params,dispatcherCallback);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * UI指令执行，例如：{@link android.widget.Toast} 或 {@link android.app.Dialog}
     * @param context
     * @param webView
     * @param commandLevel
     * @param cmd
     * @param params
     * @param dispatcherCallback
     */
    private void execUI(final Context context, final WebView webView, final int commandLevel, final String cmd, String params, final DispatcherCallback dispatcherCallback) {
        Map mapParams = gson.fromJson(params,Map.class);
        CommandsManager.getInstance().findAndExecUICommand(context, commandLevel, cmd, mapParams, new ResultBack() {
            @Override
            public void onResult(int status, String action, Object object) {
                if (status == WebContent.CONTINUE){
                    execNonUI(context,webView,commandLevel,cmd,gson.toJson(object),dispatcherCallback);
                }else {
                    handleCallback(webView,status,action,gson.toJson(object),dispatcherCallback);
                }
            }
        });
    }

    /**
     * 非ui指令执行
     * @param context
     * @param webView
     * @param commandLevel
     * @param cmd
     * @param params
     * @param dispatcherCallback
     */
    private void execNonUI(Context context, final WebView webView, int commandLevel, String cmd, String params, final DispatcherCallback dispatcherCallback) {
        Map mapParams = gson.fromJson(params,Map.class);
        if (SystemInfoUtil.inMainProcess(context, Process.myPid())){
            CommandsManager.getInstance().findAndExecNonUICommand(context, commandLevel, cmd, mapParams, new ResultBack() {
                @Override
                public void onResult(int status, String action, Object object) {
                    handleCallback(webView,status,action,gson.toJson(object),dispatcherCallback);
                }
            });
        }else {
            if (null != webAidlInterface){
                try {
                    webAidlInterface.handleWebAction(commandLevel, cmd, params, new IWebAidlCallback.Stub() {
                        @Override
                        public void onResult(int status, String action, String params) throws RemoteException {
                            handleCallback(webView,status,action,params,dispatcherCallback);
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleCallback(final WebView webView, final int status, final String action, final String json, final DispatcherCallback dispatcherCallback) {
        Log.w(TAG,String.format("callback result: action= %s, result= %s",action,json));
        MainLooper.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map params = gson.fromJson(json,Map.class);
                if (null != dispatcherCallback && !dispatcherCallback.isHandleBeforeCallback(status,action,json)){

                    return;
                }
                if (null != params.get(WebContent.NATIVE2WEB_CALLBACK) && !TextUtils.isEmpty(params.get(WebContent.NATIVE2WEB_CALLBACK).toString())){
                    if (webView instanceof BaseWebView){
                        ((BaseWebView)webView).handleCallback(json);
                    }
                }
            }
        });
    }

    public interface DispatcherCallback {
        boolean isHandleBeforeCallback(int status, String action, String json);
    }
}
