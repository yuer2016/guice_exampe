package com.yicheng.rpc.codec;

import com.yicheng.rpc.RpcFutureUtil;
import com.yicheng.rpc.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuer on 2017/1/5.
 */
@Slf4j
@Getter
public class ConnectorHandler extends SimpleChannelInboundHandler<RpcResponse>{

    private RpcFutureUtil futureUtil;

    public ConnectorHandler(RpcFutureUtil futureUtil) {
        this.futureUtil = futureUtil;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        futureUtil.notifyRpcMessage(rpcResponse);
    }


}
