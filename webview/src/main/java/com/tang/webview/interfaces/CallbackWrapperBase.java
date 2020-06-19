package com.tang.webview.interfaces;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * {@link ActionMode}是为用户界面提供一个辅助的ui菜单（类似 toolbar）
 * Description: 重写{@link ActionMode.Callback}
 */
public class CallbackWrapperBase implements ActionMode.Callback {

    private ActionMode.Callback mWrapperCustomCallback;
    private ActionMode.Callback mWrapperSystemCallback;

    public CallbackWrapperBase(ActionMode.Callback mWrapperCustomCallback, ActionMode.Callback mWrapperSystemCallback) {
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
            mWrapperCustomCallback.onDestroyActionMode(mode);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}