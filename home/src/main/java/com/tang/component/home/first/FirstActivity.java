package com.tang.component.home.first;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.tang.base.activity.MvvmActivity;
import com.tang.base.factory.BaseViewModelFactory;
import com.tang.component.home.R;
import com.tang.component.home.databinding.ModuleHomeActivityFirstBinding;
import com.tang.component.home.ui.HomeViewModel;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public class FirstActivity extends MvvmActivity<ModuleHomeActivityFirstBinding,FirstViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.module_home_activity_first;
    }

    @Override
    protected FirstViewModel getViewModel() {
        if (null == viewModel){
            viewModel = BaseViewModelFactory.getInstance().create(FirstViewModel.class).init();
        }
        return viewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
