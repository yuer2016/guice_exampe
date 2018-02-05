package com.yicheng.protos;


import com.yicheng.rpc.RpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yuer on 2017/12/6.
 */
public class FutureUtil {
    private final Map<String, GrpcFuture<?>> futures = new ConcurrentHashMap<>();
    void setRpcFuture(String mid, GrpcFuture<?> future) {
        futures.put(mid, future);
    }
    public void notifyRpcMessage(RpcResponse response) {
        GrpcFuture<?> future = futures.remove(response.getRequestId());
        if (future != null) {
            future.setResponse(response);
        }
    }
}
