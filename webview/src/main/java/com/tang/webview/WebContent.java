package com.tang.webview;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: java类作用描述
 */
public class WebContent {


    /**
     * 传递url标识
     */
    public static final String INTENT_TAG_URL = "web_url";

    public static final String CONTENT_SCHEME = "file:///android_asset/";

    public static final String CONTENT_TEST_URL = "http://10.10.20.26:8000/jsbridge/aidl.html";

    /**
     * 正常跳转,进入{@link com.tang.webview.base.NormalWebViewFragment}
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 自定义交互，进入{@link com.tang.webview.base.InterfaceWebViewFragment}
     */
    public static final int TYPE_INTERFACE = 1;

    /**
     * 自定义交互，指令存在ui
     */
    public static final int TYPE_UI = 2;

    /**
     * 分发完成
     */
    public static final int SUCCESS = 0;
    /**
     * 分发失败
     */
    public static final int FAILED = 1;
    /**
     * 继续分发command
     */
    public static final int CONTINUE = 2;

    /**
     * web与native交互的callback
     */
    public static final String WEB2NATIVE_CALLBACK = "callback";

    /**
     * native与web交互的回调参数
     */
    public static final String NATIVE2WEB_CALLBACK = "callbackName";
    public static final String NATIVE2WEB_ACTION = "action";
    public static final String NATIVE2WEB_CONTENT = "content";

    /**
     * 指令集
     */
    public static class Instruct {
        /**
         * toast提示
         */
        public static final String TOAST = "toast";
        /**
         * 弹窗
         */
        public static final String DIALOG = "dialog";
    }

    public static class ErrorCode {
        public static final int NO_METHOD = -1000;
        public static final int NO_AUTH = -1001;
        public static final int NO_LOGIN = -1002;
        public static final int ERROR_PARAM = -1003;
        public static final int ERROR_EXCEPTION = -1004;
    }

    public static class ErrorMessage {
        public static final String NO_METHOD = "方法找不到";
        public static final String NO_AUTH = "方法权限不够";
        public static final String NO_LOGIN = "尚未登录";
        public static final String ERROR_PARAM = "参数错误";
        public static final String ERROR_EXCEPTION = "未知异常";
    }
}
