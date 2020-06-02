package com.tang.component.main.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.activity.BaseActivity;
import com.tang.base.utils.LogUtil;
import com.tang.common.aroute.RouterManager;
import com.tang.common.aroute.RouterPathApi;
import com.tang.component.main.BR;
import com.tang.component.main.R;
import com.tang.component.main.databinding.ModuleMainActivityMainBinding;


/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.Main.MAIN_BASE)
public class MainActivity extends BaseActivity<ModuleMainActivityMainBinding,MainViewModel> {

    private Fragment homeFragment;
    private Fragment newsFragment;
    private Fragment findFragment;
    private Fragment liveFragment;
    private Fragment mineFragment;
    private int indexFM = 0;
    private static final String[] FRAGMENT_TAG = new String[]{"home","news","find","live","mine"};

    @Override
    protected int getLayoutId() {
        return R.layout.module_main_activity_main;
    }

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        switchFragment(indexFM);
//        viewModel.strObservable

        viewModel.mLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switchFragment(integer);
            }
        });
    }

    private void switchFragment(int position) {
        this.indexFM = position;
        setTabSelect(indexFM);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        hideFragment(transaction);
        switch (position){
            case 0:
                if (null != homeFragment){
                    transaction.show(homeFragment);
                }else {
                    homeFragment = RouterManager.getInstance().getFragment(RouterPathApi.Home.HOME_BASE);
                    transaction.add(R.id.fl_content,homeFragment,FRAGMENT_TAG[position]);
                }
                break;
            case 1:
                if (null != newsFragment){
                    transaction.show(newsFragment);
                }else {
                    newsFragment = RouterManager.getInstance().getFragment(RouterPathApi.News.NEWS_FIRST);
                    transaction.add(R.id.fl_content, newsFragment, FRAGMENT_TAG[position]);
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
                if (null != liveFragment){
                    transaction.show(liveFragment);
                }else {
                    liveFragment = RouterManager.getInstance().getFragment(RouterPathApi.Live.LIVE_FIRST);
                    transaction.add(R.id.fl_content,liveFragment,FRAGMENT_TAG[position]);
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

    private void setTabSelect(int position){
        setTabNormal();
        if (0 == position){
            dataBinding.icTab.ivTabHome.setImageResource(R.drawable.module_main_icon_tab_home_select);
        }else if (1 == position){
            dataBinding.icTab.ivTabNews.setImageResource(R.drawable.module_main_icon_tab_news_select);
        }else if (2 == position){
            dataBinding.icTab.ivTabFind.setImageResource(R.drawable.module_main_icon_tab_find_select);
        }else if (3 == position){
            dataBinding.icTab.ivTabLive.setImageResource(R.drawable.module_main_icon_tab_live_select);
        }else if (4 == position){
            dataBinding.icTab.ivTabMine.setImageResource(R.drawable. module_main_icon_tab_mine_select);
        }

    }

    private void setTabNormal(){
        dataBinding.icTab.ivTabHome.setImageResource(R.drawable.module_main_icon_tab_home_normal);
        dataBinding.icTab.ivTabNews.setImageResource(R.drawable.module_main_icon_tab_news_normal);
        dataBinding.icTab.ivTabFind.setImageResource(R.drawable.module_main_icon_tab_find_normal);
        dataBinding.icTab.ivTabLive.setImageResource(R.drawable.module_main_icon_tab_live_normal);
        dataBinding.icTab.ivTabMine.setImageResource(R.drawable.module_main_icon_tab_mine_normal);
    }
}
