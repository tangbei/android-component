package com.tang.component.news.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tang.component.news.R;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/21
 * Description: java类作用描述
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_news_fragment_news);
    }
}
