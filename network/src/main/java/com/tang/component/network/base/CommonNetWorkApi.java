/**
 * Copyright (C), 2019-2020
 * FileName: CommonNetWorkApi
 * Author: tangbei
 * Date: 2020/4/8 6:45 PM
 * Description:
 * History:
 */
package com.tang.component.network.base;

import com.tang.component.network.entity.BaseResponse;
import com.tang.component.network.errorhandle.ExceptionHandle;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * ClassName: CommonNetWorkApi
 * Description: 使用单例模式开启网络请求
 * Author: tangbei
 * Date: 2020/4/8 6:45 PM
 */
public class CommonNetWorkApi extends NetWorkApi {

    private static volatile CommonNetWorkApi mInstance;

    private static CommonNetWorkApi getInstance(){
        if (null == mInstance){
            synchronized (CommonNetWorkApi.class){
                if (null == mInstance){
                    mInstance = new CommonNetWorkApi();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取retrofit请求的代理对象
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> service){
        return getInstance().getRetrofit(service).create(service);
    }

    /**
     * 获取转换器对象
     * @param observer 观察者
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer getTransformer(Observer<T> observer){
        return mInstance.applySchedulers(observer);
    }

    /**
     * 当前项目接口返回结果处理
     * @param <T> 请求实体
     * @return
     */
    @Override
    protected <T> Function<T, T> getProjectResponseHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                if (response instanceof BaseResponse && ((BaseResponse) response).getCode() != 0){
                    //如果返回错误码，则抛出异常高，统一处理
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((BaseResponse) response).getCode();
                    exception.message = ((BaseResponse) response).getMessage() != null ? ((BaseResponse) response).getMessage() : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    /**
     * 设置当前实现类的公共请求头
     * @return
     */
    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    /**
     * 生产环境
     * @return
     */
    @Override
    public String getProduce() {
        return "https://www.toolmall.com";
    }

    /**
     * 测试环境
     * @return
     */
    @Override
    public String getTest() {
        return "http://testc.toolmall.com";
    }
}