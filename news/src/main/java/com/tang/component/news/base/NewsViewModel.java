package com.tang.component.news.base;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.tang.base.binding.command.BindingAction;
import com.tang.base.binding.command.BindingCommand;
import com.tang.base.utils.LogUtil;
import com.tang.base.viewmodel.BaseViewModel;
import com.tang.common.aroute.RouterPathApi;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/25
 * Description: java类作用描述
 */
public class NewsViewModel extends BaseViewModel<NewsModel> {


    public NewsViewModel(@NonNull Application application) {
        super(application,new NewsModel());
    }

    public BindingCommand onAIDLCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            LogUtil.d("我是aidl点击");

            Bundle bundle = new Bundle();
//            bundle.putString("web_url","https://xw.qq.com/?f=qqcom");
            startActivity(RouterPathApi.WebView.WEB_VIEW_BASE,bundle);
        }
    });
}
