package com.tang.component.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.factory.BaseViewModelFactory;
import com.tang.base.fragment.MvvmFragment;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.R;
import com.tang.component.home.databinding.ModuleHomeFragmentHomeBinding;
import com.tang.component.home.entity.TestBean;
import com.tang.component.network.environment.EnvironmentActivity;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2020/3/17.
 */
@Route(path = RouterPathApi.Home.HOME_FIRST)
public class HomeFragment extends MvvmFragment<ModuleHomeFragmentHomeBinding, HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.module_home_fragment_home;
    }

    @Override
    protected HomeViewModel getViewModel() {
        if (null == viewModel){
            viewModel = BaseViewModelFactory.getInstance().create(HomeViewModel.class).init();
        }
        return viewModel;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        TestBean testBean = new TestBean();
        testBean.setName("haha");
        viewDataBinding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.toRefresh();
            }
        });

        viewDataBinding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), EnvironmentActivity.class));
            }
        });

        viewDataBinding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
