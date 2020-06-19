package com.tang.webview.setting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tang.webview.BuildConfig;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/16
 * Description: webView默认设置管理类
 */
public class WebDefaultSettingsManager implements AgentWebSettings {

    private WebSettings mWebSettings;

    public static WebDefaultSettingsManager getInstance(){
        return new WebDefaultSettingsManager();
    }

    private WebDefaultSettingsManager(){}


    @Override
    public AgentWebSettings toSetting(WebView webView) {
        settings(webView);
        return this;
    }

    @Override
    public WebSettings getWebSettings() {
        return mWebSettings;
    }

    /**
     * 网络判断
     * @param context
     * @return
     */
    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (context != null) {
            if(Build.VERSION.SDK_INT<23){
                NetworkInfo mWiFiNetworkInfo = cm.getActiveNetworkInfo();
                if (mWiFiNetworkInfo != null) {
                    if (mWiFiNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {//WIFI
                        return true;
                    } else if (mWiFiNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {//移动数据
                        return true;
                    }
                }
            }else{
                Network network =cm.getActiveNetwork();
                if(network!=null){
                    NetworkCapabilities nc=cm.getNetworkCapabilities(network);
                    if(nc!=null){
                        if(nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){//WIFI
                            return true;
                        }else if(nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){//移动数据
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void settings(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //webView截取全屏判断，需在setContentView()前执行
            webView.enableSlowWholeDocumentDraw();
        }
        mWebSettings = webView.getSettings();
        //支持js
        mWebSettings.setJavaScriptEnabled(true);
        //WebView保留缩放功能但隐藏缩放控件
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(false);
        if (isNetworkConnected(webView.getContext())) {
            //默认缓存策略：它在缓存可获取并且没有过期的情况下加载缓存，否则通过网络获取资源
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无网络，优先从缓存读取
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             // MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
             // MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
             // MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }

        //设置WebView中加载页面字体变焦百分比，默认100，整型数。
        mWebSettings.setTextZoom(100);
        //设置是否开启数据库存储API权限，默认false，未开启，可以参考setDatabasePath(String path)
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        //设置WebView是否加载图片资源，默认true，自动加载图片
        mWebSettings.setLoadsImagesAutomatically(true);
        //设置WebView是否支持多屏窗口，参考WebChromeClient#onCreateWindow，默认false，不支持。
        mWebSettings.setSupportMultipleWindows(false);
        //是否阻塞加载网络图片  协议http or https
        mWebSettings.setBlockNetworkImage(false);
        //允许加载本地文件html  file协议
        mWebSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            mWebSettings.setAllowFileAccessFromFileURLs(false);
            //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            mWebSettings.setAllowUniversalAccessFromFileURLs(false);
        }
        //设置脚本是否允许自动打开弹窗，默认false，不允许
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置WebView底层的布局算法，参考LayoutAlgorithm#NARROW_COLUMNS，将会重新生成WebView布局
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        //设置WebView是否保存密码，默认true，保存数据。
        mWebSettings.setSavePassword(false);
        //设置WebView是否保存表单数据，默认true，保存数据。
        mWebSettings.setSaveFormData(false);
        //设置WebView是否使用预览模式加载界面。
        mWebSettings.setLoadWithOverviewMode(true);
        //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用。
        mWebSettings.setUseWideViewPort(true);
        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        mWebSettings.setDomStorageEnabled(true);
        //设置WebView是否需要设置一个节点获取焦点当被回调的时候，默认true
        mWebSettings.setNeedInitialFocus(true);
        //设置编码格式
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //设置默认字体大小
        mWebSettings.setDefaultFontSize(16);
        //设置 WebView 支持的最小字体大小，默认为 8
        mWebSettings.setMinimumFontSize(10);
        //设置是否开启定位功能，默认true，开启定位
        mWebSettings.setGeolocationEnabled(true);

        String appCacheDir = webView.getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        Log.i("WebSetting", "WebView cache dir: " + appCacheDir);
        //设置database存储位置
        mWebSettings.setDatabasePath(appCacheDir);
        //设置当前Application缓存文件路径，Application Cache API能够开启需要指定Application具备写入权限的路径
        mWebSettings.setAppCachePath(appCacheDir);
        //设置缓存的大小
        mWebSettings.setAppCacheMaxSize(1024*1024*80);

        // 用户可以自己设置useragent 设置WebView代理字符串，如果String为null或为空，将使用系统默认值
        mWebSettings.setUserAgentString("webprogress/build you agent info");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //webview网页调试
            webView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
    }
}
