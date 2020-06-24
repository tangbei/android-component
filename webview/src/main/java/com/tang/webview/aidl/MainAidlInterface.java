package com.tang.webview.aidl;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import com.google.gson.Gson;
import com.tang.base.utils.LogUtil;
import com.tang.webview.IWebAidlCallback;
import com.tang.webview.IWebAidlInterface;
import com.tang.webview.action.CommandsManager;
import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class MainAidlInterface extends IWebAidlInterface.Stub {

    private Context mContext;

    public MainAidlInterface(Context context) {
        this.mContext = context;
    }

    @Override
    public void handleWebAction(int level, String actionName, String jsonParams, IWebAidlCallback callback) throws RemoteException {
        int pid = Process.myPid();
        LogUtil.d("MainAidlInterface" , String.format("MainAidlInterface: 进程ID（%d）， WebView请求（%s）, 参数 （%s）", pid, actionName, jsonParams));
        handleRemoteAction(level,actionName,new Gson().fromJson(jsonParams, Map.class),callback);
    }

    private void handleRemoteAction(int level, String actionName, Map fromJson, final IWebAidlCallback callback) {
        CommandsManager.getInstance().findAndExecNonUICommand(mContext, level, actionName, fromJson, new ResultBack() {
            @Override
            public void onResult(int status, String action, Object object) {
                try {
                    if (null != callback) {
                        callback.onResult(status, action, new Gson().toJson(object));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
