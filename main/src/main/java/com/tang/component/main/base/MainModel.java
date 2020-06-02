package com.tang.component.main.base;

import com.tang.base.model.BaseModel;
import com.tang.base.utils.LogUtil;
import com.tang.component.main.entity.MainBean;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class MainModel extends BaseModel<MainBean> {

    public MainModel() {
        super();
    }

    @Override
    public void refresh() {
        LogUtil.d("我是main的refresh");
    }

    @Override
    protected void load() {
        LogUtil.d("我是main的load");
    }

    @Override
    public void onSuccess(MainBean mainBean) {
        LogUtil.d("我是main的onSuccess");
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtil.d("我是main的onFailure");
    }
}
