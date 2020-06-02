package com.tang.component.news.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.tang.base.factory.BaseViewModelFactory;
import com.tang.base.fragment.BaseFragment;
import com.tang.base.viewmodel.BaseViewModel;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.news.R;
import com.tang.component.news.adapter.NewsFragmentAdapter;
import com.tang.component.news.databinding.ModuleNewsFragmentNewsBinding;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/21
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.News.NEWS_FIRST)
public class NewsFragment extends Fragment {

    private NewsFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.module_news_fragment_news,container,false);
        return view;
    }

    /*@Override
    protected int getLayoutId() {
        return R.layout.module_news_fragment_news;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        adapter = new NewsFragmentAdapter(this.getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        dataBinding.viewPager.setAdapter(adapter);
        dataBinding.viewPager.setOffscreenPageLimit(1);
        dataBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        dataBinding.tabLayout.setupWithViewPager(dataBinding.viewPager);

    }*/
}
