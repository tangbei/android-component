package com.tang.webview.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.tang.webview.client.ProgressWebChromeClient;
import com.tang.webview.progressbar.IndicatorHandler;
import com.tang.webview.progressbar.WebProgressBar;
import java.lang.ref.WeakReference;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/18
 * Description: 含有进度条的{@link android.webkit.WebView}
 */
public class ProgressWebView extends BaseWebView {

    private WebProgressBar mWebProgressBar;
    private IndicatorHandler mIndicatorHandler;
    private ProgressHandler progressHandler;

    public ProgressWebView(Context context) {
        super(context);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public class ProgressHandler extends Handler {
        private WeakReference<IndicatorHandler> mWeakReference;

        public ProgressHandler(IndicatorHandler indicatorHandler) {
            this.mWeakReference = new WeakReference<>(indicatorHandler);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            IndicatorHandler indicatorHandler = mWeakReference.get();
            int newProgress = (int) msg.obj;
            indicatorHandler.setProgress(newProgress);
        }
    }

    private void init(){
        mWebProgressBar = new WebProgressBar(mContext);
        mWebProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mWebProgressBar.setVisibility(GONE);
        addView(mWebProgressBar);
        mIndicatorHandler = IndicatorHandler.getInstance().infactProgressView(mWebProgressBar);
        progressHandler = new ProgressHandler(mIndicatorHandler);
        setWebChromeClient(new ProgressWebChromeClient(progressHandler));
    }
}
