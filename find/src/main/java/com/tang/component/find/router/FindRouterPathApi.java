package com.tang.component.find.router;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.common.aroute.ARoutePathApi;
import com.tang.component.find.now.FindFragment;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/19
 * Description: java类作用描述
 */
@Route(path = ARoutePathApi.FIND_FIRST)
public class FindRouterPathApi implements ARoutePathApi {

    @Override
    public void init(Context context) {
    }

    @Override
    public Fragment getFragment() {
        return new FindFragment();
    }
}
