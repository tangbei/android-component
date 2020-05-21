package com.tang.component.home.first;

import com.tang.base.model.MvvmBaseModel;
import com.tang.component.home.entity.TestBean;
import com.tang.component.network.beans.BaseResponse;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public class FirstModel extends MvvmBaseModel<BaseResponse<List<TestBean>>> {

    @Override
    public void refresh() {

    }

    @Override
    protected void load() {

    }

    @Override
    public void onSuccess(BaseResponse<List<TestBean>> listBaseResponse) {

    }

    @Override
    public void onFailure(Throwable e) {

    }
}
