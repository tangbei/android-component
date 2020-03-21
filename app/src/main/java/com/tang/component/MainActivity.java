package com.tang.component;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.tang.base.utils.LogUtil;
import com.tang.component.databinding.ActivityMainBinding;
import com.tang.component.fragment.ClassifyFragment;
import com.tang.component.fragment.HomeFragment;
import com.tang.component.fragment.MineFragment;
import com.tang.component.fragment.RecommendFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    private static final String[] tabTitles = new String[]{"首页","推荐","分类","个人中心"};
    private static final String[] FRAGMENT_TAG = new String[]{"home","recommend","classify","mine"};
    private HomeFragment homeFragment;
    private RecommendFragment recommendFragment;
    private ClassifyFragment classifyFragment;
    private MineFragment mineFragment;
    private int indexFM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        initTab();
        switchFragment(indexFM);
    }

    private void initTab() {
        for (int i = 0; i < tabTitles.length; i++){
            mainBinding.tabLayout.addTab(mainBinding.tabLayout.newTab().setText(tabTitles[i]));
        }
        mainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                    homeFragment = new HomeFragment();
//                    homeFragment.setData("我是进入的内动哦");
                    transaction.add(R.id.fl_content,homeFragment,FRAGMENT_TAG[position]);
                }
                break;
            case 1:
                if (null != recommendFragment){
                    transaction.show(recommendFragment);
                }else {
                    recommendFragment =new RecommendFragment();
                    transaction.add(R.id.fl_content,recommendFragment,FRAGMENT_TAG[position]);
                }
                break;
            case 2:
                if (null != classifyFragment){
                    transaction.show(classifyFragment);
                }else {
                    classifyFragment = new ClassifyFragment();
                    transaction.add(R.id.fl_content, classifyFragment, FRAGMENT_TAG[position]);
                }
                break;
            case 3:
                if (null != mineFragment){
                    transaction.show(mineFragment);
                }else {
                    mineFragment = new MineFragment();
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
        if (null != recommendFragment){
            transaction.hide(recommendFragment);
        }
        if (null != classifyFragment){
            transaction.hide(classifyFragment);
        }
        if (null != mineFragment) {
            transaction.hide(mineFragment);
        }
    }

}
