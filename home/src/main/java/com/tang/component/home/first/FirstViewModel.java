package com.tang.component.home.first;

import com.tang.base.viewmodel.MvvmBaseViewModel;
import com.tang.component.home.entity.TestBean;
import com.tang.component.network.beans.BaseResponse;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public class FirstViewModel extends MvvmBaseViewModel<FirstModel, BaseResponse<List<TestBean>>> {

    @Override
    protected FirstViewModel init() {
        return null;
    }
}
