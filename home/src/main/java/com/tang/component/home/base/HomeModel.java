package com.tang.component.home.base;

import android.app.Application;

import com.tang.base.model.BaseModel;
import com.tang.base.observer.BaseObserver;
import com.tang.base.utils.LogUtil;
import com.tang.component.home.entity.TestBean;
import com.tang.component.home.service.HttpServiceApi;
import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.network.entity.BaseResponse;
import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/16
 * Description: java类作用描述
 */
public class HomeModel extends BaseModel<List<TestBean>> {

    private List<TestBean> list;

    private static final String cache_key = "testBean";

    public HomeModel(Application application){
        super(application,cache_key);
    }


    @Override
    public void refresh() {
        load();
    }

    @Override
    protected void load() {
        CommonNetWorkApi.getService(HttpServiceApi.class).getIndex()
                .compose(CommonNetWorkApi.getTransformer(
                        new BaseObserver<BaseResponse<List<TestBean>>>(this)));
    }

    @Override
    public void onSuccess(List<TestBean> testBean) {
        this.loadSuccess(testBean);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
        LogUtil.d("我是请求失败结果==="+e.getMessage());
    }
}
