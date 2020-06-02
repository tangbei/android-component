package com.tang.component.home.first;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tang.base.binding.command.BindingAction;
import com.tang.base.binding.command.BindingCommand;
import com.tang.base.model.BaseModel;
import com.tang.base.utils.LogUtil;
import com.tang.base.viewmodel.BaseViewModel;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public class FirstViewModel extends BaseViewModel<FirstModel> {

    public FirstViewModel(@NonNull Application application) {
        super(application,new FirstModel(application));
    }

    @Override
    public void onLoadFinish(BaseModel model, Object data) {
        LogUtil.d("我是first返回结果");
    }

    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            model.getCacheDataAndLoad();
        }
    });

}
