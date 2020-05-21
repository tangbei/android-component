package com.tang.component;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tang.base.activity.MvvmActivity;
import com.tang.base.utils.LogUtil;
import com.tang.base.viewmodel.MvvmBaseViewModel;
import com.tang.common.aroute.RouterManager;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.databinding.ActivityMainBinding;
import com.tang.component.entity.TestBean;

public class MainActivity extends MvvmActivity<ActivityMainBinding, MvvmBaseViewModel> {

    private static final String[] tabTitles = new String[]{"首页","推荐","发现","分类","个人中心"};
    private static final String[] FRAGMENT_TAG = new String[]{"home","recommend","find","classify","mine"};
    private Fragment homeFragment;
    private Fragment liveFragment;
    private Fragment findFragment;
    private Fragment newsFragment;
    private Fragment mineFragment;
    private int indexFM = 0;

    private TestBean testBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return BR.testBean;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTab();
        switchFragment(indexFM);
    }

    private void initTab() {
        for (int i = 0; i < tabTitles.length; i++){
            viewDataBinding.tabLayout.addTab(viewDataBinding.tabLayout.newTab().setText(tabTitles[i]));
        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int position) {
        this.indexFM = position;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        hideFragment(transaction);
        switch (position){
            case 0:
                if (null != homeFragment){
                    transaction.show(homeFragment);
                }else {
                    homeFragment = RouterManager.getInstance().getFragment(RouterPathApi.Home.HOME_FIRST);
                    transaction.add(R.id.fl_content,homeFragment,FRAGMENT_TAG[position]);
                }
                break;
            case 1:
                if (null != liveFragment){
                    transaction.show(liveFragment);
                }else {
                    liveFragment = RouterManager.getInstance().getFragment(RouterPathApi.Live.LIVE_FIRST);
                    transaction.add(R.id.fl_content,liveFragment,FRAGMENT_TAG[position]);
                }
                break;
            case 2:
                if (null != findFragment){
                    transaction.show(findFragment);
                }else {
                    findFragment = RouterManager.getInstance().getFragment(RouterPathApi.Find.FIND_FIRST);
                    transaction.add(R.id.fl_content, findFragment, FRAGMENT_TAG[position]);
                }
                break;
            case 3:
                if (null != newsFragment){
                    transaction.show(newsFragment);
                }else {
                    newsFragment = RouterManager.getInstance().getFragment(RouterPathApi.News.NEWS_FIRST);
                    transaction.add(R.id.fl_content, newsFragment, FRAGMENT_TAG[position]);
                }
                break;
            case 4:
                if (null != mineFragment){
                    transaction.show(mineFragment);
                }else {
                    mineFragment = RouterManager.getInstance().getFragment(RouterPathApi.Mine.MINE_FIRST);
                    transaction.add(R.id.fl_content,mineFragment,FRAGMENT_TAG[position]);
                }
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
        LogUtil.d("点击："+indexFM);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (null != homeFragment){
            transaction.hide(homeFragment);
        }
        if (null != liveFragment){
            transaction.hide(liveFragment);
        }
        if (null != findFragment){
            transaction.hide(findFragment);
        }
        if (null != newsFragment){
            transaction.hide(newsFragment);
        }
        if (null != mineFragment) {
            transaction.hide(mineFragment);
        }
    }

}
