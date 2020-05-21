package com.tang.component.executor;

import android.os.Handler;
import android.os.Looper;

import com.tang.base.utils.LogUtil;

import java.util.concurrent.Executor;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/29
 * Description: java类作用描述
 */
public class TestExecutor implements Executor {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command) {

        mHandler.post(command);
//        System.out.println(command);
    }
}
