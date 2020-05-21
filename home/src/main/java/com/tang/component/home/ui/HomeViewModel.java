package com.tang.component.home.ui;

import com.tang.base.viewmodel.MvvmBaseViewModel;
import com.tang.component.home.entity.TestBean;
import com.tang.component.network.beans.BaseResponse;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/16
 * Description: java类作用描述
 */
public class HomeViewModel extends MvvmBaseViewModel<HomeModel, BaseResponse<List<TestBean>>> {

    public HomeViewModel init(){
        model = new HomeModel();
        return this;
    }



}
