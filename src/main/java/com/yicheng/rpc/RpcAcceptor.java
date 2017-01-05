package com.yicheng.rpc;

import java.io.IOException;

/**
 * Created by yuer on 2017/1/5.
 */
public interface RpcAcceptor {

    void setHost(String host);

    void setport(int port);

    void start() throws IOException;

    void stop() throws IOException;
    //public void setProcessor(RpcProcessor processor);

}
