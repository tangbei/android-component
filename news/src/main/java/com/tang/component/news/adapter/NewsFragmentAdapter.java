package com.tang.component.news.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/25
 * Description: java类作用描述
 */
public class NewsFragmentAdapter extends FragmentPagerAdapter {

    public NewsFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
