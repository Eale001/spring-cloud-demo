package com.eale.common.response;

import java.io.Serializable;

/**
 * @author: hzh
 * @date: 2021-03-22
 */
public class ObjectResponse<T> extends BaseResponse implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
