package com.yicheng.netty;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yicheng.guice.MyModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Created by yuer on 2017/1/3.
 */
public class ServiceHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Injector injector = Guice.createInjector(new MyModule());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        String className = rpcRequest.getClassName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        Class<?> serviceClass = injector.getInstance(Class.forName(className)).getClass();
        String methodName = rpcRequest.getMethodName();
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        response.setResult(method.invoke(serviceClass,parameters));
        channelHandlerContext.writeAndFlush(response);

    }
}
