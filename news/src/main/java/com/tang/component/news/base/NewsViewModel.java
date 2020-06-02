package com.tang.component.news.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tang.base.model.BaseModel;
import com.tang.base.viewmodel.BaseViewModel;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/25
 * Description: java类作用描述
 */
public class NewsViewModel extends BaseViewModel<NewsModel> {

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onLoadFinish(BaseModel model, Object data) {
        super.onLoadFinish(model, data);
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        super.onLoadFail(model, prompt);
    }

    @Override
    public void onCreate() {
        model = new NewsModel();
        model.register(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
