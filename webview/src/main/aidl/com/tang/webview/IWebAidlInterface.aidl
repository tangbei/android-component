// IMyAidlInterface.aidl
package com.tang.webview;

import com.tang.webview.IWebAidlCallback;

interface IWebAidlInterface {

    /**
     * level:
     * actionName: 不同的action行为
     * jsonParams: 内容
     * IWebAidlCallback: 回调
     */
    void handleWebAction(int level, String actionName, String jsonParams, in IWebAidlCallback callback);
}
