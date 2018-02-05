package com.yicheng.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by yuer on 2017/3/31.
 */
@Slf4j
public class ProxyHandler implements InvocationHandler {

    private final Object object;

    public ProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("doSomething".equalsIgnoreCase(method.getName())){
            log.info("代理模式 ... ...");
        }
        return method.invoke(this.object,args);
    }


}
