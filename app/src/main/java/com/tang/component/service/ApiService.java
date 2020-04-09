/**
 * Copyright (C), 2019-2020
 * FileName: AppService
 * Author: tangbei
 * Date: 2020/4/8 7:02 PM
 * Description:
 * History:
 */
package com.tang.component.service;

import com.tang.component.network.beans.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * ClassName: ApiService
 * Description: java描述
 * Author: tangbei
 * Date: 2020/4/8 7:02 PM
 */
public interface ApiService {

    @GET("/app/index/maogongRecomend")
    Observable<BaseResponse<List<TestBean>>> getIndex();

}