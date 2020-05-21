package com.tang.common.aroute;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: 路由地址配置接口
 */
public interface ARoutePathApi extends IProvider {

    String HOME_ROUTER = "/home/";
    String HOME_FIRST = HOME_ROUTER + "home_first";

    String FIND_ROUTER = "/find/";
    String FIND_FIRST = FIND_ROUTER + "find_first";

    Fragment getFragment();
}
