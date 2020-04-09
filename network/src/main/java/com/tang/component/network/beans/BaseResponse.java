/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: BaseResponse
 * Author: tangbei
 * Date: 2020/4/9 10:01 AM
 * Description:
 * History:
 */
package com.tang.component.network.beans;

/**
 * Author: tangbei
 * Date: 2020/4/9 10:01 AM
 * Description: java类作用描述
 */
public class BaseResponse<T> {

    private int code;

    private T data;

    private String message;

    private long time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
