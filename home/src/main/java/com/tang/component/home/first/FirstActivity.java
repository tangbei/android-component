package com.tang.component.home.first;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.activity.BaseActivity;
import com.tang.base.factory.BaseViewModelFactory;
import com.tang.base.router.ActivityRouterManager;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.BR;
import com.tang.component.home.R;
import com.tang.component.home.databinding.ModuleHomeActivityFirstBinding;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.Home.HOME_FIRST)
public class FirstActivity extends BaseActivity<ModuleHomeActivityFirstBinding,FirstViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.module_home_activity_first;
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        /*dataBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPathApi.Home.HOME_SECOND).navigation();
//                ActivityRouterManager.getInstance().startActivity(RouterPathApi.Home.HOME_SECOND,null);
            }
        });*/

    }
}
