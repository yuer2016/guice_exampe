package com.yicheng.rpc;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by yuer on 2017/1/5.
 */
@Slf4j
public class RpcProxy {
    @Getter
    @Setter
    private  RpcConnector connector;

    public <T> T getProxy(final Class<?> clazz){
        InvocationHandler handler = (proxy,method,args) ->{
            RpcRequest request = new RpcRequest();
            request.setRequestId(UUID.randomUUID().toString());
            request.setClassName(clazz.getName());
            request.setMethodName(method.getName());
            request.setParameterTypes(method.getParameterTypes());
            request.setParameters(args);
            RpcResponse invoke = connector.invoke(request);
            log.info("返回数据"+invoke);
            if (invoke == null) return null;
            return invoke.getResult();
        };
        return (T) Proxy.newProxyInstance(RpcProxy.class.getClassLoader(),new Class[]{clazz},handler);
    }
}
