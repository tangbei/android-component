package com.tang.component.executor;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/29
 * Description: 线程优先级factory
 */
public class TestPriorityThreadFactory implements ThreadFactory {

    private int mPriorityThread;

    public TestPriorityThreadFactory(int mPriorityThread) {
        this.mPriorityThread = mPriorityThread;
    }

    @Override
    public Thread newThread(Runnable r) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //设置线程优先级
                Process.setThreadPriority(mPriorityThread);

                r.run();
            }
        };
        return newThread(runnable);
    }
}
