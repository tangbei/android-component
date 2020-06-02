package com.tang.component.home.service;

import com.tang.component.home.entity.TestBean;
import com.tang.component.network.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/4/30
 * Description: java类作用描述
 */
public interface HttpServiceApi {

    @GET("/app/index/maogongRecomend")
    Observable<BaseResponse<List<TestBean>>> getIndex();
}
