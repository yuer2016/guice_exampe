package com.yicheng.rpc;

import lombok.SneakyThrows;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcClient {
    private RpcProxy proxy;
    private RpcConnector connector;
    @SneakyThrows
    public RpcClient(String host,int port) {
        connector = new NettyRpcConnector();
        connector.setHost(host);
        connector.setPort(port);
        proxy = new RpcProxy();
        proxy.setConnector(connector);
        connector.start();
    }

    public <T> T refer(Class<T> clazz){
        return proxy.getProxy(clazz);
    }
    @SneakyThrows
    public void stop(){
        connector.stop();
    }
}
