/**
 * Copyright (C), 2019-2020
 * FileName: NetWorkApi
 * Author: tangbei
 * Date: 2020/4/8 5:04 PM
 * Description:
 * History:
 */
package com.tang.component.network.base;

import com.tang.component.network.environment.EnvironmentActivity;
import com.tang.component.network.environment.IEnvironment;
import com.tang.component.network.errorhandle.HttpErrorHandler;
import com.tang.component.network.interceptor.RequestInterceptor;
import com.tang.component.network.interceptor.ResponseInterceptor;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ClassName: NetWorkApi
 * @Description: 网络请求基类
 * @Author: tangbei
 * @Date: 2020/4/8 5:04 PM
 */
public abstract class NetWorkApi implements IEnvironment {

    private static INetWorkConfig netWorkConfig;
    private static Map<String,Retrofit> retrofitMap = new HashMap<>();
    private OkHttpClient mOkHttpClient;
    private String BaseURL = "";
    /**
     * 网络环境切换
     */
    private static boolean isEnvironment = false;

    public NetWorkApi() {
        if (isEnvironment){
            BaseURL = getProduce();
        }else {
            BaseURL = getTest();
        }
    }

    /**
     * 初始化操作
     * @param config
     */
    public static void init(INetWorkConfig config){
        netWorkConfig = config;
        isEnvironment = EnvironmentActivity.isOfficialEnvironment(config.getApplicationContext());
    }

    /**
     * 获取retrofit对象
     * @param service
     * @return
     */
    protected Retrofit getRetrofit(Class service){
        //获取map中存的retrofit，如果存在，则return。不存在，则创建
        if (null != retrofitMap.get(BaseURL+service.getName())){
            return retrofitMap.get(BaseURL+service.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BaseURL);//设置url
        builder.client(getOkHttpClient());//设置okhttp
        builder.addConverterFactory(GsonConverterFactory.create());//设置gson转换器
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitMap.put(BaseURL + service.getName(), retrofit);
        return retrofit;
    }

    /**
     * 获取okHttp对象
     * @return
     */
    private OkHttpClient getOkHttpClient(){
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            int cacheSize = 100 * 1024 * 1024; // 10MB
            okHttpClientBuilder.cache(new Cache(netWorkConfig.getNetWorkCacheDir(), cacheSize));//设置缓存路径和缓存大小
            okHttpClientBuilder.addInterceptor(new RequestInterceptor(netWorkConfig));//设置公共请求头
            okHttpClientBuilder.addInterceptor(new ResponseInterceptor());//设置请求回调
            //是否打印日志
            if (netWorkConfig != null &&(netWorkConfig.isDebug())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }

    /**
     * 转换器，并配置线程位置和错误处理
     * @param observer
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T,T> applySchedulers(final Observer<T> observer){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>) upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new HttpErrorHandler<T>())//okhttp错误处理
                        .map(getProjectResponseHandler());//接口请求结果处理
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    /**
     * 接口异常提前处理
     * @param <T>
     * @return
     */
    protected abstract <T> Function<T,T> getProjectResponseHandler();

    /**
     * 请求头
     * @return
     */
    protected abstract Interceptor getInterceptor();


}
