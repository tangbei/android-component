package com.tang.component.main.base;

import android.app.Application;
import androidx.annotation.NonNull;
import com.tang.base.utils.LogUtil;
import com.tang.base.viewmodel.BaseViewModel;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class MainViewModel extends BaseViewModel<MainModel> {

    public MainViewModel(@NonNull Application application) {
        super(application,new MainModel());
        model.refresh();
        LogUtil.d("BaseViewModel-->"+this+"-->"+"supper");
    }

    @Override
    public void onCreate() {
    }


    public void selectTab(int position){
        LogUtil.d("点击位置:"+position);

//        strObservable.set(position+"");
        mLiveData.setValue(position);
        if (0 == position){
            getTabHome();
        }
    }

    public boolean getTabHome(){
        return true;
    }
}
