package com.tang.component.home.screenadapt;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.activity.BaseActivity;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.R;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.Home.HOME_SCREEN_ADAPT)
public class ScreenAdaptActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.module_home_activity_screen_adapt;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
