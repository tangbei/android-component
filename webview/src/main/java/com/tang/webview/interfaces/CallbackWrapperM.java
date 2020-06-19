package com.tang.webview.interfaces;

import android.graphics.Rect;
import android.os.Build;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: 重写{@link android.view.ActionMode.Callback2}
 * 是在{@link android.os.Build.VERSION_CODES#M} 下的
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CallbackWrapperM extends ActionMode.Callback2 {

    private ActionMode.Callback mWrapperCustomCallback;
    private ActionMode.Callback mWrapperSystemCallback;

    public CallbackWrapperM(ActionMode.Callback mWrapperCustomCallback, ActionMode.Callback mWrapperSystemCallback) {
        this.mWrapperCustomCallback = mWrapperCustomCallback;
        this.mWrapperSystemCallback = mWrapperSystemCallback;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return mWrapperCustomCallback.onCreateActionMode(mode,menu);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return mWrapperCustomCallback.onPrepareActionMode(mode,menu);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return mWrapperCustomCallback.onActionItemClicked(mode,item);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        try {
            mWrapperCustomCallback.onDestroyActionMode(mode);
            mWrapperSystemCallback.onDestroyActionMode(mode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
        if (mWrapperCustomCallback instanceof ActionMode.Callback2){
            ((ActionMode.Callback2) mWrapperCustomCallback).onGetContentRect(mode,view,outRect);
        }else if (mWrapperSystemCallback instanceof ActionMode.Callback2){
            ((ActionMode.Callback2) mWrapperSystemCallback).onGetContentRect(mode,view,outRect);
        }else {
            super.onGetContentRect(mode, view, outRect);
        }
    }
}
