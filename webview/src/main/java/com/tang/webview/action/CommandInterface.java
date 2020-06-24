package com.tang.webview.action;

import android.content.Context;

import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public interface CommandInterface {

    /**
     * 注册指令名
     * @return
     */
    String name();

    void exec(Context context, Map params, ResultBack resultBack);
}
