package com.tang.base.screenadapt.provider;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tang.base.screenadapt.ScreenSizeConfig;
import com.tang.base.utils.BaseUtil;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/28
 * Description: 屏幕适配的内容提供者
 * 默认是app一打开，就会初始化执行ScreenSizeConfig
 */
public class ScreenAdaptProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        Context application = getContext().getApplicationContext();
        if (null == application){
            application = BaseUtil.getApplicationByReflect();
        }

//        ScreenSizeConfig.getInstance().setExcludeFontScale(true).init((Application) application);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
