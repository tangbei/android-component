package com.tang.webview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.base.activity.BaseActivity;
import com.tang.base.utils.LogUtil;
import com.tang.common.aroute.RouterPathApi;
import com.tang.webview.base.BaseWebViewFragment;
import com.tang.webview.base.InterfaceWebViewFragment;
import com.tang.webview.base.NormalWebViewFragment;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/4
 * Description: java类作用描述
 */
@Route(path = RouterPathApi.WebView.WEB_VIEW_BASE)
public class WebViewActivity extends BaseActivity {

    private BaseWebViewFragment mWebViewFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.module_webview_activity_webview;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra("web_url");
        LogUtil.d("我是webview接受的数据:"+ url);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (null == mWebViewFragment){
            if (!TextUtils.isEmpty(url)){
                mWebViewFragment = NormalWebViewFragment.getInstance(url);
            }else {
                mWebViewFragment = InterfaceWebViewFragment.newInstance(WebContent.CONTENT_SCHEME + "aidl.html");
            }
        }
        fragmentTransaction.replace(R.id.fragment,mWebViewFragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null != mWebViewFragment && mWebViewFragment instanceof BaseWebViewFragment){
            if (mWebViewFragment.onKeyDown(keyCode,event)){
                return mWebViewFragment.onKeyDown(keyCode,event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
