package com.yicheng.rpc;

import com.yicheng.rpc.codec.Exporter;
import lombok.SneakyThrows;

/**
 * Created by yuer on 2017/1/5.
 */
public class RpcServer {
    private NettyRpcAcceptor acceptor;
    private Exporter exporter;
    @SneakyThrows
    public RpcServer(String host, int port) {
        acceptor = new NettyRpcAcceptor();
        exporter = Exporter.INSTANCE;
        acceptor.setHost(host);
        acceptor.setport(port);
        acceptor.setExporter(exporter);
        acceptor.start();
    }

    public void exporter(Class<?> clazz, Object obj){
        exporter.export(clazz,obj);
    }

}
