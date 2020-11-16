package com.tang.component.home.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.constant.Constants;
import com.tang.base.fragment.BaseFragment;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.BR;
import com.tang.component.home.R;
import com.tang.component.home.databinding.ModuleHomeFragmentHomeBinding;
import com.tang.component.network.environment.EnvironmentActivity;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2020/3/17.
 */
@Route(path = RouterPathApi.Home.HOME_BASE)
public class HomeFragment extends BaseFragment<ModuleHomeFragmentHomeBinding, HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.module_home_fragment_home;
    }

    @Override
    protected int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        dataBinding.tvEnv.setText(Constants.BUILD_ENV == 1 ? "开发环境" : (Constants.BUILD_ENV == 2 ? "测试环境" : "线上环境"));

        dataBinding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), EnvironmentActivity.class));
            }
        });

        /*dataBinding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }
}
