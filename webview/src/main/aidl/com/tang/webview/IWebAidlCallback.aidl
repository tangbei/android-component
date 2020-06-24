// IWebAidlCallback.aidl
package com.tang.webview;

// Declare any non-default types here with import statements

interface IWebAidlCallback {
    /**
     * status指令执行状态
     * action 指令
     * params 参数内容
     */
    void onResult(int status, String action, String params);
}
