package com.yicheng.rpc;

/**
 * Created by yuer on 2017/1/6.
 */
public interface Callback {

    void onSuccess(Object result);

    void onError(Throwable throwable);
}
