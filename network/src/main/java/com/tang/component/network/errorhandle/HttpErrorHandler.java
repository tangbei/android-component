/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: HttpErrorHandler
 * Author: tangbei
 * Date: 2020/4/9 10:36 AM
 * Description:
 * History:
 */
package com.tang.component.network.errorhandle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Author: tangbei
 * Date: 2020/4/9 10:36 AM
 * Description: http错误处理
 * 1、http请求的相关错误：例如：404，403，socket timeout等等；
 * 2、应用数据的错误会抛RuntimeException，最后也会走到这里来统一处理
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
