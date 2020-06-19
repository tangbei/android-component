package com.tang.webview.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.tang.webview.interfaces.BaseWebViewCallBack;
import com.tang.webview.view.ProgressWebView;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/18
 * Description: java类作用描述
 */
public class ProgressWebChromeClient extends WebChromeClient {

    private Handler progressHandler;

    public ProgressWebChromeClient(Handler handler) {
        this.progressHandler = handler;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Message msg = new Message();
        if (newProgress == 100){
            msg.obj = newProgress;
            progressHandler.sendMessageDelayed(msg,200);
        }else {
            if (newProgress < 10){
                newProgress = 10;
            }
            msg.obj = newProgress;
            progressHandler.sendMessage(msg);
        }
        super.onProgressChanged(view, newProgress);
    }
}
