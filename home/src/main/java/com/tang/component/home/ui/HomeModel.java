package com.tang.component.home.ui;

import com.tang.base.model.MvvmBaseModel;
import com.tang.base.utils.LogUtil;
import com.tang.component.home.entity.TestBean;
import com.tang.component.home.service.HttpServiceApi;
import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.network.beans.BaseResponse;
import com.tang.component.network.observer.BaseObserver;
import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/16
 * Description: java类作用描述
 */
public class HomeModel extends MvvmBaseModel<BaseResponse<List<TestBean>>> {


    public HomeModel() {
        super();
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        CommonNetWorkApi.getService(HttpServiceApi.class).getIndex()
                .compose(CommonNetWorkApi.getTransformer(
                        new BaseObserver<BaseResponse<List<TestBean>>>(this)
                ));
    }

    @Override
    public void onSuccess(BaseResponse<List<TestBean>> o) {
        loadSuccess(o);
        LogUtil.d("我是请求成功结果===");
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
        LogUtil.d("我是请求失败结果==="+e.getMessage());
    }
}
