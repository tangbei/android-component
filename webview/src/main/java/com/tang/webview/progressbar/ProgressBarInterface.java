package com.tang.webview.progressbar;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/18
 * Description: {@link android.webkit.WebView} 的 {@link android.os.Process}的代理类
 */
public interface ProgressBarInterface {

    /**
     * 显示
     */
    void show();

    /**
     * 隐藏
     */
    void hide();

    /**
     * 重置
     */
    void reset();

    /**
     * 设置进度
     * @param newProgress
     */
    void setProgress(int newProgress);
}
