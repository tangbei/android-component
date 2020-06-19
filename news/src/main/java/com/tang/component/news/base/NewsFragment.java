package com.tang.component.news.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.fragment.BaseFragment;
import com.tang.base.utils.LogUtil;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.news.BR;
import com.tang.component.news.R;
import com.tang.component.news.databinding.ModuleNewsFragmentNewsBinding;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/21
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.News.NEWS_FIRST)
public class NewsFragment extends BaseFragment<ModuleNewsFragmentNewsBinding,NewsViewModel> {

    private boolean connected;

    @Override
    protected int getLayoutId() {
        return R.layout.module_news_fragment_news;
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

//        bindServices();
        viewModel.mLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0){
                }
            }
        });
    }

    private void bindServices(){
        Intent intent = new Intent();
        intent.setPackage("com.tang.webview");
        intent.setAction("com.tang.webview.action");
        this.getActivity().bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            bookManager = IBookManager.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };
}
