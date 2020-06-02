package com.tang.component.home.second;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.activity.BaseActivity;
import com.tang.base.router.ActivityRouterManager;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.R;
import com.tang.component.home.databinding.ModuleHomeActivitySecondBinding;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/27
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.Home.HOME_SECOND)
public class SecondActivity extends BaseActivity<ModuleHomeActivitySecondBinding,SecondViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.module_home_activity_second;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

//        List<Activity> list = ActivityRouterManager.getInstance().getActivityList();
    }
}
