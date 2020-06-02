package com.tang.component;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.common.aroute.RouterPathApi;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_app_activity_splash);

        inMain();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1 * 1000);*/
    }

    private void inMain() {
        ARouter.getInstance().build(RouterPathApi.Main.MAIN_BASE).navigation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
