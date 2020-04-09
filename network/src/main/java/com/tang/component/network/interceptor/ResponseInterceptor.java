/**
 * Copyright (C), 2019-2020
 * FileName: ResponseInterCeptor
 * Author: tangbei
 * Date: 2020/4/8 6:20 PM
 * Description:
 * History:
 */
package com.tang.component.network.interceptor;

import com.tang.base.utils.LogUtil;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @ClassName: ResponseInterCeptor
 * @Description: 请求结果处理
 * @Author: tangbei
 * @Date: 2020/4/8 6:20 PM
 */
public class ResponseInterceptor implements Interceptor {

    private static final String TAG = "Response_callBack:";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        //打印请求结果的时间
        LogUtil.d(TAG,"requestTime-->"+requestTime);
        return chain.proceed(chain.request());
    }
}