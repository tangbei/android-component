package com.tang.component.home.base;

import android.app.Application;
import androidx.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.binding.command.BindingAction;
import com.tang.base.binding.command.BindingCommand;
import com.tang.base.binding.command.BindingConsumer;
import com.tang.base.model.BaseModel;
import com.tang.base.utils.LogUtil;
import com.tang.base.viewmodel.BaseViewModel;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.home.entity.TestBean;

import java.util.List;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/16
 * Description: java类作用描述
 */
public class HomeViewModel extends BaseViewModel<HomeModel> {

    private List<TestBean> list;

    public HomeViewModel(@NonNull Application application) {
        super(application,new HomeModel(application));
        LogUtil.w("我是home");
    }

    @Override
    public void onLoadFinish(BaseModel model, Object data) {
        list = (List<TestBean>) data;
        LogUtil.w("拿到结果并处理");
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            model.getCacheDataAndLoad();
        }
    });

    /*public BindingConsumer onClickCommand = new BindingConsumer() {

        @Override
        protected void call() {
            LogUtil.e("我是ooo点击事件");
        }
    };*/

    public BindingConsumer onScreenClickCommand = new BindingConsumer() {

        @Override
        public void accept(Object o) throws Exception {
            LogUtil.e("我是ooo点击事件");
            ARouter.getInstance().build(RouterPathApi.Home.HOME_FIRST).navigation();
//            startActivity(RouterPathApi.Home.HOME_FIRST,null);
        }
    };


}
