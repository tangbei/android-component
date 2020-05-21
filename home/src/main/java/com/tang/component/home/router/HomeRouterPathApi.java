package com.tang.component.home.router;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.common.aroute.ARoutePathApi;
import com.tang.component.home.ui.HomeFragment;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/19
 * Description: java类作用描述
 */
@Route(path = ARoutePathApi.HOME_FIRST)
public class HomeRouterPathApi implements ARoutePathApi {

    @Override
    public Fragment getFragment() {
        return new HomeFragment();
    }

    @Override
    public void init(Context context) {

    }
}
