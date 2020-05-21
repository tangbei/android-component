package com.tang.common.aroute;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.base.utils.LogUtil;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/19
 * Description: ARouter的管理类
 */
public class ARouteServiceManager {

    private static final String TAG = "ARouteServiceManager";

    /**
     * 获取对应跳转
     * @param clazz
     * @param path
     * @param <T>
     * @return
     */
    public static <T extends IProvider> T provide(Class<T> clazz,String path){
        if (TextUtils.isEmpty(path)){
            return null;
        }
        LogUtil.d(TAG,"跳转-->"+path);
        IProvider iProvider = null;
        try {
            //根据path的简单跳转
            iProvider = (IProvider) ARouter.getInstance().build(path).navigation();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T) iProvider;

    }
}
