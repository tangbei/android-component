package com.tang.component.home.first;

import android.app.Application;

import com.tang.base.model.BaseModel;
import com.tang.base.observer.BaseObserver;
import com.tang.component.home.entity.TestBean;
import com.tang.component.home.service.HttpServiceApi;
import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.network.entity.BaseResponse;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public class FirstModel extends BaseModel<List<TestBean>> {

    private static String cache_key = "testBean";

    public FirstModel(Application application) {
        super(application,cache_key);
    }

    @Override
    public void refresh() {

    }

    @Override
    protected void load() {
        CommonNetWorkApi.getService(HttpServiceApi.class).getIndex()
                .compose(CommonNetWorkApi.getTransformer(
                        new BaseObserver<BaseResponse<List<TestBean>>>(this)));
    }

    @Override
    public void onSuccess(List<TestBean> listBaseResponse) {
        this.loadSuccess(listBaseResponse);
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
