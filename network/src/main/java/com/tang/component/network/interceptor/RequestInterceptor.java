/**
 * Copyright (C), 2019-2020
 * FileName: RequestIntercepter
 * Author: tangbei
 * Date: 2020/4/8 6:14 PM
 * Description:
 * History:
 */
package com.tang.component.network.interceptor;

import com.tang.component.network.base.INetWorkConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: RequestIntercepter
 * @Description: 全局公共请求头
 * @Author: tangbei
 * @Date: 2020/4/8 6:14 PM
 */
public class RequestInterceptor implements Interceptor {

    private INetWorkConfig netWorkConfig;

    public RequestInterceptor(INetWorkConfig netWorkConfig) {
        this.netWorkConfig = netWorkConfig;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}