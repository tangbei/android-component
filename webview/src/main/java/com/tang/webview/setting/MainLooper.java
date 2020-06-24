package com.tang.webview.setting;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class MainLooper extends Handler {

    private static MainLooper instance = new MainLooper(Looper.getMainLooper());

    public MainLooper(Looper mainLooper) {
        super(mainLooper);
    }

    public static MainLooper getInstance(){
        return instance;
    }

    public static void runOnUiThread(Runnable runnable){
        if (Looper.getMainLooper().equals(Looper.myLooper())){
            runnable.run();
        }else {
            instance.post(runnable);
        }
    }
}
